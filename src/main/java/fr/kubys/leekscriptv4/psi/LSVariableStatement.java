// This is a generated file. Not intended for manual editing.
package fr.kubys.leekscriptv4.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface LSVariableStatement extends PsiElement {

  @Nullable
  LSEos getEos();

  @NotNull
  LSModifier getModifier();

  @NotNull
  List<LSVariableDeclaration> getVariableDeclarationList();

  boolean isGlobal();

}
