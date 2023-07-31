// This is a generated file. Not intended for manual editing.
package fr.kubys.leekscriptv4.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LSSingleExpression extends PsiElement {

  @Nullable
  LSAdditiveExpression getAdditiveExpression();

  @Nullable
  LSAssignExpression getAssignExpression();

  @Nullable
  LSBitwiseExpression getBitwiseExpression();

  @Nullable
  LSCompareExpression getCompareExpression();

  @Nullable
  LSLogicAndExpression getLogicAndExpression();

  @Nullable
  LSLogicOrExpression getLogicOrExpression();

  @Nullable
  LSMultiplicativeExpression getMultiplicativeExpression();

  @Nullable
  LSPrefixExpression getPrefixExpression();

  @Nullable
  LSShiftExpression getShiftExpression();

  @Nullable
  LSTernaryExpression getTernaryExpression();

}
