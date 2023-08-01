// This is a generated file. Not intended for manual editing.
package fr.kubys.leekscriptv4.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LSTernaryExpression extends PsiElement {

  @NotNull
  List<LSAdditiveExpression> getAdditiveExpressionList();

  @NotNull
  List<LSBitwiseExpression> getBitwiseExpressionList();

  @NotNull
  List<LSCompareExpression> getCompareExpressionList();

  @NotNull
  List<LSLogicAndExpression> getLogicAndExpressionList();

  @NotNull
  List<LSLogicOrExpression> getLogicOrExpressionList();

  @NotNull
  List<LSMultiplicativeExpression> getMultiplicativeExpressionList();

  @NotNull
  List<LSPrefixExpression> getPrefixExpressionList();

  @NotNull
  List<LSShiftExpression> getShiftExpressionList();

  @NotNull
  LSSingleExpression getSingleExpression();

  @Nullable
  LSTernaryExpression getTernaryExpression();

  @NotNull
  PsiElement getOpColon();

  @NotNull
  PsiElement getOpTernary();

}
