// This is a generated file. Not intended for manual editing.
package fr.kubys.leekscriptv4.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LSCompareExpression extends PsiElement {

  @NotNull
  List<LSAdditiveExpression> getAdditiveExpressionList();

  @NotNull
  List<LSBitwiseExpression> getBitwiseExpressionList();

  @Nullable
  LSCompareExpression getCompareExpression();

  @NotNull
  List<LSMultiplicativeExpression> getMultiplicativeExpressionList();

  @NotNull
  List<LSPrefixExpression> getPrefixExpressionList();

  @NotNull
  List<LSShiftExpression> getShiftExpressionList();

  @Nullable
  PsiElement getOpEquals();

  @Nullable
  PsiElement getOpGe();

  @Nullable
  PsiElement getOpGt();

  @Nullable
  PsiElement getOpIdentityEquals();

  @Nullable
  PsiElement getOpIdentityNotEquals();

  @Nullable
  PsiElement getOpLe();

  @Nullable
  PsiElement getOpLt();

  @Nullable
  PsiElement getOpNotEquals();

}
