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

public class LSFunctionExpressionImpl extends ASTWrapperPsiElement implements LSFunctionExpression {

  public LSFunctionExpressionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LSVisitor visitor) {
    visitor.visitFunctionExpression(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LSVisitor) accept((LSVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public LSBlock getBlock() {
    return findChildByClass(LSBlock.class);
  }

  @Override
  @Nullable
  public LSFormalParameterList getFormalParameterList() {
    return findChildByClass(LSFormalParameterList.class);
  }

  @Override
  @NotNull
  public PsiElement getKwFunction() {
    return findNotNullChildByType(KW_FUNCTION);
  }

  @Override
  @Nullable
  public PsiElement getOpLparen() {
    return findChildByType(OP_LPAREN);
  }

  @Override
  @Nullable
  public PsiElement getOpRparen() {
    return findChildByType(OP_RPAREN);
  }

}
