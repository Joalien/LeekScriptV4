package fr.kubys.leekscriptv4.parser;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static fr.kubys.leekscriptv4.psi.LSTypes.*;

%%

%{
  public _LeekScriptLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _LeekScriptLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

IDENTIFIER=([:letter:]|_)[a-zA-Z_0-9]*
NUMBER=[0-9]+(\.[0-9]*)?
STRING=('([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")
COMMENT="//".*
C_STYLE_COMMENT="/"\*(.|\\n)*?\*"/"
DOC_COMMENT="/"\*\*(.|\\n)*?\*"/"

%%
<YYINITIAL> {
  {WHITE_SPACE}          { return WHITE_SPACE; }

  "function"             { return KW_FUNCTION; }
  "var"                  { return KW_VAR; }
  "global"               { return KW_GLOBAL; }
  "if"                   { return KW_IF; }
  "else"                 { return KW_ELSE; }
  "while"                { return KW_WHILE; }
  "do"                   { return KW_DO; }
  "for"                  { return KW_FOR; }
  "in"                   { return KW_IN; }
  "null"                 { return KW_NULL; }
  "true"                 { return KW_TRUE; }
  "false"                { return KW_FALSE; }
  "return"               { return KW_RETURN; }
  "break"                { return KW_BREAK; }
  "continue"             { return KW_CONTINUE; }
  "++"                   { return OP_INC; }
  "--"                   { return OP_DEC; }
  "+"                    { return OP_PLUS; }
  "-"                    { return OP_MINUS; }
  "**"                   { return OP_POW; }
  "*"                    { return OP_TIMES; }
  "/"                    { return OP_DIVIDE; }
  "%"                    { return OP_MODULO; }
  ";"                    { return OP_SEMICOLON; }
  ":"                    { return OP_COLON; }
  "("                    { return OP_LPAREN; }
  ")"                    { return OP_RPAREN; }
  "["                    { return OP_LBRACKET; }
  "]"                    { return OP_RBRACKET; }
  "{"                    { return OP_LBRACE; }
  "}"                    { return OP_RBRACE; }
  ","                    { return OP_COMMA; }
  "+="                   { return OP_PLUS_EQ; }
  "-="                   { return OP_MINUS_EQ; }
  "*="                   { return OP_TIMES_EQ; }
  "/="                   { return OP_DIVIDE_EQ; }
  "&="                   { return OP_AND_EQ; }
  "|="                   { return OP_OR_EQ; }
  "^="                   { return OP_XOR_EQ; }
  "="                    { return OP_ASSIGN; }
  "!"                    { return OP_EXCLAMATION_MARK; }
  "not"                  { return OP_NOT; }
  "||"                   { return OP_LOGICAL_OR; }
  "&&"                   { return OP_LOGICAL_AND; }
  "or"                   { return OP_OR; }
  "and"                  { return OP_AND; }
  ">>>"                  { return OP_UNSIGNED_RSHIFT; }
  ">>"                   { return OP_RSHIFT; }
  "<<"                   { return OP_LSHIFT; }
  "^"                    { return OP_XOR; }
  "<="                   { return OP_LE; }
  "<"                    { return OP_LT; }
  ">="                   { return OP_GE; }
  ">"                    { return OP_GT; }
  "==="                  { return OP_IDENTITY_EQUALS; }
  "=="                   { return OP_EQUALS; }
  "!=="                  { return OP_IDENTITY_NOT_EQUALS; }
  "!="                   { return OP_NOT_EQUALS; }
  "|"                    { return OP_BINARY_OR; }
  "&"                    { return OP_BINARY_AND; }
  "@"                    { return OP_REFERENCE; }
  "?"                    { return OP_TERNARY; }

  {IDENTIFIER}           { return IDENTIFIER; }
  {NUMBER}               { return NUMBER; }
  {STRING}               { return STRING; }
  {COMMENT}              { return COMMENT; }
  {C_STYLE_COMMENT}      { return C_STYLE_COMMENT; }
  {DOC_COMMENT}          { return DOC_COMMENT; }

}

[^] { return BAD_CHARACTER; }
