// This is a generated file. Not intended for manual editing.
package fr.kubys.leekscriptv4.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static fr.kubys.leekscriptv4.psi.LSTypes.*;
import fr.kubys.leekscriptv4.psi.LSNamedElementImpl;
import fr.kubys.leekscriptv4.psi.*;
import com.intellij.navigation.ItemPresentation;

public class LSVariableDeclarationImpl extends LSNamedElementImpl implements LSVariableDeclaration {

  public LSVariableDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LSVisitor visitor) {
    visitor.visitVariableDeclaration(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LSVisitor) accept((LSVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public LSInitialiser getInitialiser() {
    return findChildByClass(LSInitialiser.class);
  }

  @Override
  @NotNull
  public PsiElement getIdentifier() {
    return findNotNullChildByType(IDENTIFIER);
  }

  @Override
  public ItemPresentation getPresentation() {
    return LSPsiImplUtil.getPresentation(this);
  }

}
