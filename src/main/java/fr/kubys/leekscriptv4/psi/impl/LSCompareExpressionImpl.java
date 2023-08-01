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

public class LSCompareExpressionImpl extends ASTWrapperPsiElement implements LSCompareExpression {

  public LSCompareExpressionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LSVisitor visitor) {
    visitor.visitCompareExpression(this);
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
  @Nullable
  public LSCompareExpression getCompareExpression() {
    return findChildByClass(LSCompareExpression.class);
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
  public PsiElement getOpEquals() {
    return findChildByType(OP_EQUALS);
  }

  @Override
  @Nullable
  public PsiElement getOpGe() {
    return findChildByType(OP_GE);
  }

  @Override
  @Nullable
  public PsiElement getOpGt() {
    return findChildByType(OP_GT);
  }

  @Override
  @Nullable
  public PsiElement getOpIdentityEquals() {
    return findChildByType(OP_IDENTITY_EQUALS);
  }

  @Override
  @Nullable
  public PsiElement getOpIdentityNotEquals() {
    return findChildByType(OP_IDENTITY_NOT_EQUALS);
  }

  @Override
  @Nullable
  public PsiElement getOpLe() {
    return findChildByType(OP_LE);
  }

  @Override
  @Nullable
  public PsiElement getOpLt() {
    return findChildByType(OP_LT);
  }

  @Override
  @Nullable
  public PsiElement getOpNotEquals() {
    return findChildByType(OP_NOT_EQUALS);
  }

}
