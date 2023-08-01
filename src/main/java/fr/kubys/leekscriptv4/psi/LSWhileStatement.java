// This is a generated file. Not intended for manual editing.
package fr.kubys.leekscriptv4.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LSWhileStatement extends PsiElement {

  @Nullable
  LSBlock getBlock();

  @Nullable
  LSBreakStatement getBreakStatement();

  @Nullable
  LSContinueStatement getContinueStatement();

  @Nullable
  LSDoWhileStatement getDoWhileStatement();

  @Nullable
  LSEmptyStatement getEmptyStatement();

  @Nullable
  LSExpressionStatement getExpressionStatement();

  @Nullable
  LSForStatement getForStatement();

  @Nullable
  LSIfStatement getIfStatement();

  @Nullable
  LSReturnStatement getReturnStatement();

  @Nullable
  LSVariableStatement getVariableStatement();

  @NotNull
  LSWhileCondition getWhileCondition();

  @Nullable
  LSWhileStatement getWhileStatement();

}
