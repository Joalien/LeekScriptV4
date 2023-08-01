// This is a generated file. Not intended for manual editing.
package fr.kubys.leekscriptv4.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LSIfStatement extends PsiElement {

  @Nullable
  LSElseBlock getElseBlock();

  @Nullable
  LSSingleExpression getSingleExpression();

  @Nullable
  LSThenBlock getThenBlock();

  @NotNull
  PsiElement getKwIf();

  @Nullable
  PsiElement getOpLparen();

  @Nullable
  PsiElement getOpRparen();

}
