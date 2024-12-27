package cloud.jlkjy.cn.gpt.ui.components.popup;


import cloud.jlkjy.cn.gpt.ui.components.popup.recordprovider.PopupListSelector;
import cloud.jlkjy.cn.gpt.utils.ColorUtil;
import cloud.jlkjy.cn.gpt.utils.PopUtil;
import cloud.jlkjy.cn.gpt.model.PopupRecord;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;

import com.intellij.ui.CollectionListModel;
import com.intellij.ui.ListUtil;
import com.intellij.ui.ScrollingUtil;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextField;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Getter
@Setter
public class PopupPanel {
    private SuggestPromptAbstractPopup promptSuggestPopup;
    private JBList<PopupRecord> popupList;
    private Project project;
    private Point location;
    private String triggerComponent;
    private AtomicLong lastChoozeTime = new AtomicLong(0L);

    private Debouncer searchSuggestPromptDebouncer;
    private String currentPromptType;
    private String originPromptType;
    private int currentLevel;
    private JComponent owner;
    private JScrollPane popupScrollPane;

    private JPanel contentPanel;
    private JBTextField searchTextArea;
    private PopupRender renderer;
    Stack<PageItem> pageStack = new Stack<>();
    private PageItem currentPage;

    @Data
    @Builder
    static class PageItem {
        private String type;
        private int level;
        private PopupRecord parentRecord;
    }

    public PopupPanel(Project project) {
        this.project = project;
        this.searchSuggestPromptDebouncer = new Debouncer();
        initSelector();
    }

    private void initSelector() {
        if (this.popupList == null) {
            this.popupList = new JBList<>();
            this.popupList.setVisibleRowCount(10);
            this.popupList.setOpaque(false);
            this.renderer = new PopupRender(this, this.owner);
            this.popupList.setCellRenderer(this.renderer);
            this.popupList.setBorder(BorderFactory.createEmptyBorder());
            ListUtil.installAutoSelectOnMouseMove(this.popupList);
            ScrollingUtil.installActions(this.popupList);


            this.popupList.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    PopupRecord record = PopupPanel.this.popupList.getSelectedValue();
                    PopupPanel.this.handleChosenSuggest(record);
                    e.consume();
                }
            });
        }

        this.popupScrollPane = new JBScrollPane();
        this.popupScrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.popupScrollPane.setViewportView(this.popupList);
        this.popupScrollPane.setOpaque(false);
        this.popupScrollPane.setVerticalScrollBarPolicy(20);
        this.popupScrollPane.setHorizontalScrollBarPolicy(31);
        this.contentPanel = new JPanel(new BorderLayout());
        this.contentPanel.setOpaque(false);
        this.contentPanel.add(this.popupScrollPane, "Center");
        this.contentPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        this.popupList.setBackground(ColorUtil.getListBackground());
        this.popupList.getEmptyText().setText("");
    }

    private void initPopupPanel() {
        this.promptSuggestPopup = new SuggestPromptAbstractPopup(this.popupList, this.project, "contextPanel".equals(this.triggerComponent));
    }

    private void handleChosenSuggest(PopupRecord record) {
        if (record == null || record.getText() == null)
            return;
        if ("RETURN_FUNCTION".equals(record.getType())) {
            this.pageStack.pop();
            doDisplaySuggestPrompts(this.owner, this.location);
            return;
        }
        if (record.isCanDrillDown() && record.getDrillDownType() != null && !record.getDrillDownType().isEmpty()) {
            int level = this.currentPage.getLevel()+1;
            this.pageStack.push(PageItem.builder().type(record.getDrillDownType()).level(level).parentRecord(record).build());
            doDisplaySuggestPrompts(this.owner, this.location);
            return;
        }
        // TODO 具体提交上下文的逻辑

    }

    public void displaySuggestPrompts(String type, Point location, String triggerComponent, JComponent owner) {
        this.pageStack.clear();
        this.pageStack.push(PageItem.builder().type(type).level(1).build());
        this.owner = owner;
        this.location = location;
        this.originPromptType = type;
        this.triggerComponent = triggerComponent;
        this.renderer.setHostComponent(owner);
        doDisplaySuggestPrompts(owner, location);
        this.lastChoozeTime.getAndSet(0L);
    }

    private void doDisplaySuggestPrompts(JComponent owner, Point location) {
        PageItem currentPage = this.pageStack.peek();
        this.searchSuggestPromptDebouncer.debounce(() -> {
            this.currentPage = currentPage;
            List<PopupRecord> allSuggestPrompt = getSuggestPromptList("");
            SwingUtilities.invokeLater(()->{
                PopupPanel.this.suggestPrompts(allSuggestPrompt, owner, location, "");
            });
        },100L, TimeUnit.MILLISECONDS);
    }


    public List<PopupRecord> getSuggestPromptList(String query) {
        List<PopupRecord> allSuggestPrompt = new ArrayList<>();
        if (this.pageStack.size() > 1) {
            allSuggestPrompt.add(PopupRecord.returnBuilder(this.currentPage.getType()).build());
        }
        List<cloud.jlkjy.cn.gpt.model.PopupRecord> popupRecords = PopupListSelector.select(this.currentPage.getType()).getProvider().listSuggestPrompts(this.project, query, this.currentPage.getParentRecord());
        allSuggestPrompt.addAll(popupRecords);
        return allSuggestPrompt;
    }


    public void suggestPrompts(List<PopupRecord> suggestPrompts, JComponent owner, Point location, String query) {
        if (this.promptSuggestPopup != null && CollectionUtils.isEmpty(suggestPrompts)) {
            this.promptSuggestPopup.cancel();
            return;
        }
        if (CollectionUtils.isEmpty(suggestPrompts))
            return;
        if (PopUtil.isPopUsable(this.promptSuggestPopup)) {
            this.popupList.setModel(new CollectionListModel<>(suggestPrompts));
            ScrollingUtil.ensureIndexIsVisible(this.popupList, 0, 1);
            this.popupList.getEmptyText().setText("");
            adjustJbPopSize(owner);
            int i = (int) this.promptSuggestPopup.getContent().getPreferredSize().getHeight();
            Point newPoint = new Point(location.x, location.y - i);
            this.promptSuggestPopup.setLocation(newPoint);
//            if (this.lastPopupHeight != i) {
//                this.promptSuggestPopup.setLocation(newPoint);
//                this.lastPopupHeight = i;
//            }
            this.popupScrollPane.getVerticalScrollBar().setValue(0);
            this.popupList.setSelectedIndex(0);
            return;
        }
        if (this.promptSuggestPopup != null)
            this.promptSuggestPopup.cancel();
        initPopupPanel();
//        this.suggestPromptScrollPane.getVerticalScrollBar().setValue(0);
        this.popupList.setModel(new CollectionListModel<>(suggestPrompts));
        this.popupList.setSelectedIndex(0);
        this.popupList.getEmptyText().setText("");
        adjustJbPopSize(owner);
        int currentHeight = (int) this.promptSuggestPopup.getSize().getHeight();
//        if (this.popupListener != null)
//            this.promptSuggestPopup.addListener(this.popupListener);
        this.promptSuggestPopup.showInScreenCoordinates(owner, new Point(location.x, location.y - currentHeight));
//        addSearchPanel(location);
//        this.lastPopupHeight = currentHeight;
//        if (this.searchTextArea != null)
//            this.searchTextArea.requestFocus();
    }

    private void adjustJbPopSize(JComponent owner) {
        int width = (owner.getSize()).width;
        Dimension scrollPaneSize = this.popupScrollPane.getSize();
        scrollPaneSize.height = Math.min((this.popupList.getPreferredSize()).height, 320);
        if (this.searchTextArea != null) {
            this.contentPanel.setSize(new Dimension((this.contentPanel.getSize()).width, (scrollPaneSize.getSize()).height + 32 + 16));
        } else {
            this.contentPanel.setSize(new Dimension((this.contentPanel.getSize()).width, (scrollPaneSize.getSize()).height + 16));
        }
        this.popupScrollPane.getViewport().setSize(scrollPaneSize);
        Dimension dimension = new Dimension(width, (this.contentPanel.getSize()).height);
        this.promptSuggestPopup.setSize(dimension);
    }











    public static class Debouncer {
        private static final Logger log = Logger.getInstance(Debouncer.class);

        private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        private Future<?> prev;

        private String id;

        private final Lock lock = new ReentrantLock();

        public void debounce(Runnable runnable, long delay, TimeUnit unit) {
            this.lock.lock();
            try {
                if (this.prev != null && !this.prev.isDone())
                    this.prev.cancel(false);
                this.prev = this.scheduler.schedule(runnable, delay, unit);
            } finally {
                this.lock.unlock();
            }
        }

        public Future<?> debounce(String id, Callable<?> callable, long delay, TimeUnit unit) {
            this.lock.lock();
            try {
                if (this.prev != null)
                    this.prev.cancel(false);
                this.prev = this.scheduler.schedule(callable, delay, unit);
                this.id = id;
                return this.prev;
            } finally {
                this.lock.unlock();
            }
        }

        public void shutdown() {
            this.lock.lock();
            try {
                if (this.prev != null) {
                    this.prev.cancel(false);
                    this.prev = null;
                }
            } finally {
                this.lock.unlock();
            }
        }
    }

}
