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

public class LSLogicOrExpressionImpl extends ASTWrapperPsiElement implements LSLogicOrExpression {

  public LSLogicOrExpressionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LSVisitor visitor) {
    visitor.visitLogicOrExpression(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LSVisitor) accept((LSVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<LSAdditiveExpression> getAdditiveExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LSAdditiveExpression.class);
  }

  @Override
  @NotNull
  public List<LSBitwiseExpression> getBitwiseExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LSBitwiseExpression.class);
  }

  @Override
  @NotNull
  public List<LSCompareExpression> getCompareExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LSCompareExpression.class);
  }

  @Override
  @NotNull
  public List<LSLogicAndExpression> getLogicAndExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LSLogicAndExpression.class);
  }

  @Override
  @Nullable
  public LSLogicOrExpression getLogicOrExpression() {
    return findChildByClass(LSLogicOrExpression.class);
  }

  @Override
  @NotNull
  public List<LSMultiplicativeExpression> getMultiplicativeExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LSMultiplicativeExpression.class);
  }

  @Override
  @NotNull
  public List<LSPrefixExpression> getPrefixExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LSPrefixExpression.class);
  }

  @Override
  @NotNull
  public List<LSShiftExpression> getShiftExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, LSShiftExpression.class);
  }

  @Override
  @Nullable
  public PsiElement getOpLogicalOr() {
    return findChildByType(OP_LOGICAL_OR);
  }

  @Override
  @Nullable
  public PsiElement getOpOr() {
    return findChildByType(OP_OR);
  }

}
