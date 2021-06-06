package com.github.stokito.IdeaJol.inspection;

import com.github.stokito.IdeaJol.toolwindow.JolView;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import org.jetbrains.annotations.NotNull;

public class ShowJolQuickFix implements LocalQuickFix {
    private static final Logger LOG = Logger.getInstance("#com.github.stokito.IdeaJol.inspection.ShowJolQuickFix");

    @NotNull
    @Override
    public String getFamilyName() {
        return "Show Object Layout";
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor problemDescriptor) {
        PsiElement psiClassIdentifier = problemDescriptor.getPsiElement();
        if (!(psiClassIdentifier instanceof PsiIdentifier)) {
            return;
        }
        try {
            PsiClass psiClass = (PsiClass) psiClassIdentifier.getParent();
            JolView.getInstance(project).showLayoutForClass(psiClass);
            ToolWindowManager.getInstance(project).getToolWindow("JOL").activate(null);
        } catch (Exception ex) {
            LOG.error("Unable to generate layout", ex);
        }
    }
}
