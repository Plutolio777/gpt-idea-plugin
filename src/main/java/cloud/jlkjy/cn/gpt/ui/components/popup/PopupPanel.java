package cloud.jlkjy.cn.gpt.ui.components.popup;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.ScrollingUtil;
import com.intellij.ui.components.JBList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    public PopupPanel(Project project) {
        this.project = project;
        init();
    }

    private void init() {
        if (this.popupList == null) {
            this.popupList = new JBList<>();
            this.popupList.setVisibleRowCount(10);
            this.popupList.setOpaque(false);
            ListCellRenderer<PopupRecord> popupRender = new PopupRender();
            this.popupList.setCellRenderer(popupRender);
            this.popupList.setBorder(BorderFactory.createEmptyBorder());
            ScrollingUtil.installActions(this.popupList);


            this.popupList.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    PopupRecord record = PopupPanel.this.popupList.getSelectedValue();
                    PopupPanel.this.handleChosenSuggest(record);
                    e.consume();
                }
            });
        }
    }

    private void handleChosenSuggest(PopupRecord record) {
        Messages.showInfoMessage("This is an information message.", "Info");
    }

    public void displaySuggestPrompts(String type, Point location, String triggerComponent) {
        this.location = location;
        this.originPromptType = type;
        this.triggerComponent = triggerComponent;
        doDisplaySuggestPrompts(type, location, 1);
        this.lastChoozeTime.getAndSet(0L);
    }

    private void doDisplaySuggestPrompts(String promptType, Point location, int level) {
        this.searchSuggestPromptDebouncer.debounce(() -> {
            this.currentPromptType = promptType;
            this.currentLevel = level;
            List<PopupRecord> allSuggestPrompt = getSuggestPromptList("");
            SwingUtilities.invokeLater(());
        },100L, TimeUnit.MILLISECONDS);
    }

    public static class Debouncer {
        private static final Logger log = Logger.getInstance(Debouncer.class);

        private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        private Future prev;

        private String id;

        private Lock lock = new ReentrantLock();

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

        public Future debounce(String id, Callable<?> callable, long delay, TimeUnit unit) {
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

    public List<PopupRecord> getSuggestPromptList(String query) {
        List<PopupRecord> allSuggestPrompt = new ArrayList<>();


        return allSuggestPrompt;
    }

}
