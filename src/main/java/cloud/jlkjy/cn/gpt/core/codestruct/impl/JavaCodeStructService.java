package cloud.jlkjy.cn.gpt.core.codestruct.impl;

import cloud.jlkjy.cn.gpt.model.PopupRecord;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandListener;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Iconable;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class JavaCodeStructService  extends AbstractCodeStructServiceActivity {
    private static final Logger LOGGER = Logger.getInstance(JavaCodeStructService.class);

    public JavaCodeStructService() {
        super("JAVA");
        LOGGER.info(String.format("code struct %s create success", type));
    }

    @Override
    public List<PopupRecord> getFileStruct(String path, @NotNull Project project) {
        List<PopupRecord> popupRecords = new ArrayList<>();
        StructureViewTreeElement root = this.getStructureViewTreeElement(path, project);
        if (root == null) {
            return List.of();
        }
        ApplicationManager.getApplication().runReadAction(()->extractElements(root, popupRecords));
        return popupRecords;
    }

    private void extractElements(StructureViewTreeElement element, List<PopupRecord> records) {
        for (TreeElement child : element.getChildren()) {
           doExtractElements((StructureViewTreeElement) child, records, 0);
        }
    }


    private void doExtractElements(StructureViewTreeElement element, List<PopupRecord> records, int level) {
        // 获取当前节点的值
        Object value = element.getValue();

        // 判断节点是否是 Java 类或方法
        if (value instanceof PsiClass psiClass) {
            PopupRecord classRecord = createPopupRecordForClass(psiClass);
            classRecord.setLevel(level);
            records.add(classRecord);

            // 递归解析子节点
            for (TreeElement child : element.getChildren()) {
                doExtractElements((StructureViewTreeElement) child, records, level + 1);
            }

            // 提取类中的方法
            for (PsiMethod method : psiClass.getMethods()) {
                PopupRecord methodRecord = createPopupRecordForMethod(method);
                methodRecord.setLevel(level + 1);
                records.add(methodRecord);
            }
        }
    }

    private PopupRecord createPopupRecordForClass(PsiClass psiClass) {

        String className = psiClass.getQualifiedName(); // 获取类的全限定名
        return PopupRecord.builder()
                .text(className)
                .id("CODE STRUCT")
                .icon(psiClass.getIcon(Iconable.ICON_FLAG_VISIBILITY))
                .type("CLASS")
                .enabled(true)
                .selectHintText("点击添加代码片段")
                .build();
    }


    private PopupRecord createPopupRecordForMethod(PsiMethod method) {
        String methodName = method.getName();

        // 获取返回类型
        PsiType returnType = method.getReturnType();
        String returnTypeString = (returnType != null) ? returnType.getPresentableText() : "void";

        // 获取参数列表
        PsiParameter[] parameters = method.getParameterList().getParameters();
        StringBuilder parameterList = new StringBuilder();
        for (PsiParameter parameter : parameters) {
            // 参数类型 + 参数名
            parameterList.append(parameter.getType().getPresentableText())
                    .append(" ")
                    .append(parameter.getName())
                    .append(", ");
        }
        if (!parameterList.isEmpty()) {
            parameterList.setLength(parameterList.length() - 2); // 去掉最后的逗号和空格
        }
        // 获取方法名

        return PopupRecord.builder()
                .text(methodName + "(" + parameterList + "): " + returnTypeString)
                .id("CODE STRUCT")
                .icon(method.getIcon(Iconable.ICON_FLAG_VISIBILITY))
                .type("METHOD")
                .enabled(true)
                .selectHintText("点击添加代码片段")
                .build();
    }
}
