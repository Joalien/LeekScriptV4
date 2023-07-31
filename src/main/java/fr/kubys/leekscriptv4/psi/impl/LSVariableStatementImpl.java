// This is a generated file. Not intended for manual editing.
package fr.kubys.leekscriptv4.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static fr.kubys.leekscriptv4.psi.LSTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import fr.kubys.leekscriptv4.psi.*;

public class LSVariableStatementImpl extends ASTWrapperPsiElement implements LSVariableStatement {

  public LSVariableStatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LSVisitor visitor) {
    visitor.visitVariableStatement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LSVisitor) accept((LSVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public LSEos getEos() {
    return findChildByClass(LSEos.class);
  }

  @Override
  @NotNull
  public LSModifier getModifier() {
    return findNotNullChildByClass(LSModifier.class);
  }

  @Override
  @NotNull
  public List<LSVariableDeclaration> getVariableDeclarationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LSVariableDeclaration.class);
  }

  @Override
  public boolean isGlobal() {
    return LSPsiImplUtil.isGlobal(this);
  }

}
