package Analizador;

//importaciones
import java_cup.runtime.Symbol;

%%

//definicion de variables
%{
    String cadena = "";
%}

// Definiciones iniciales
%init{
    yyline = 1;
    yycolumn = 1;
%init}

//declaraciones de caracteristicas de jflex
%cup
%class scanner //nombre de la clase
%public //acceso de la clase
%line //conteo de lineas
%column //conteo de columnas
%char //conteo de caracteres
%full //reconocimiento de caracteres
%debug //depuracion
%ignorecase //quitar la distincion entre mayusculas y minusculas (case insensitive)

//estados
%state CADENA

// definir los simbolos del sistema
//Simbolos
INCREMENTO = "++"
DECREMENTO = "--"
MAS = "+"
MENOS = "-"
POTENCIA = "**"
MULT = "*"
DIV = "/"
MOD = "%"
NOT = "!"
IGUAL = "="
IGUALACION = "=="
DIFERENCIA = "!="
MENORIGUAL = "<="
MENOR = "<"
MAYORIGUAL = ">="
MAYOR = ">"
OR = "||"
AND = "&&"
XOR = "^"

PAR1 = "("
PAR2 = ")"
LLAVE1 = "{"
LLAVE2 = "}"
FINCADENA = ";"

//Palabras reservadas
VAR = "VAR"
CONST = "CONST"
INT = "INT"
DOUBLE = "DOUBLE"
IF = "IF"
ELSEIF = "ELSE IF"
ELSE = "ELSE"
MATCH = "MATCH"
WHILE = "WHILE"
FOR = "FOR"
DO = "DO"
BREAK = "BREAK"
CONTINUE = "CONTINUE"
PRINTLN = "PRINTLN"

//Expresiones regulares
BLANCOS = [\ \r\t\n\f]+
ID = [a-zA-Z][a-zA-Z0-9_]*
CADENA = [\"][^\"]*[\"]
CHAR = ['][a-zA-Z0-9\ \r\t\n\f][']
ENTERO = [0-9]+
DECIMAL = [0-9]+"."[0-9]+


%%
//Simbolos
<YYINITIAL> {INCREMENTO}  {return new Symbol(sym.INCREMENTO, yyline, yycolumn,yytext());}
<YYINITIAL> {DECREMENTO}  {return new Symbol(sym.DECREMENTO, yyline, yycolumn,yytext());}
<YYINITIAL> {MAS}         {return new Symbol(sym.MAS, yyline, yycolumn,yytext());}
<YYINITIAL> {MENOS}       {return new Symbol(sym.MENOS, yyline, yycolumn,yytext());}
<YYINITIAL> {POTENCIA}    {return new Symbol(sym.POTENCIA, yyline, yycolumn,yytext());}
<YYINITIAL> {MULT}        {return new Symbol(sym.MULT, yyline, yycolumn,yytext());}
<YYINITIAL> {DIV}         {return new Symbol(sym.DIV, yyline, yycolumn,yytext());}
<YYINITIAL> {MOD}         {return new Symbol(sym.MOD, yyline, yycolumn,yytext());}
<YYINITIAL> {NOT}         {return new Symbol(sym.NOT, yyline, yycolumn,yytext());}
<YYINITIAL> {IGUAL}       {return new Symbol(sym.IGUAL, yyline, yycolumn,yytext());}
<YYINITIAL> {IGUALACION}  {return new Symbol(sym.IGUALACION, yyline, yycolumn,yytext());}
<YYINITIAL> {DIFERENCIA}  {return new Symbol(sym.DIFERENCIA, yyline, yycolumn,yytext());}
<YYINITIAL> {MENORIGUAL}  {return new Symbol(sym.MENORIGUAL, yyline, yycolumn,yytext());}
<YYINITIAL> {MENOR}       {return new Symbol(sym.MENOR, yyline, yycolumn,yytext());}
<YYINITIAL> {MAYORIGUAL}  {return new Symbol(sym.MAYORIGUAL, yyline, yycolumn,yytext());}
<YYINITIAL> {MAYOR}       {return new Symbol(sym.MAYOR, yyline, yycolumn,yytext());}
<YYINITIAL> {OR}          {return new Symbol(sym.OR, yyline, yycolumn,yytext());}
<YYINITIAL> {AND}         {return new Symbol(sym.AND, yyline, yycolumn,yytext());}
<YYINITIAL> {XOR}         {return new Symbol(sym.XOR, yyline, yycolumn,yytext());}

<YYINITIAL> {PAR1}        {return new Symbol(sym.PAR1, yyline, yycolumn,yytext());}
<YYINITIAL> {PAR2}        {return new Symbol(sym.PAR2, yyline, yycolumn,yytext());}
<YYINITIAL> {LLAVE1}      {return new Symbol(sym.LLAVE1, yyline, yycolumn,yytext());}
<YYINITIAL> {LLAVE2}      {return new Symbol(sym.LLAVE2, yyline, yycolumn,yytext());}
<YYINITIAL> {FINCADENA}   {return new Symbol(sym.FINCADENA, yyline, yycolumn,yytext());}

//Palabras reservadas
<YYINITIAL> {VAR}           {return new Symbol(sym.VAR, yyline, yycolumn,yytext());}
<YYINITIAL> {CONST}         {return new Symbol(sym.CONST, yyline, yycolumn,yytext());}
<YYINITIAL> {INT}           {return new Symbol(sym.INT, yyline, yycolumn,yytext());}
<YYINITIAL> {DOUBLE}        {return new Symbol(sym.DOUBLE, yyline, yycolumn,yytext());}
<YYINITIAL> {IF}            {return new Symbol(sym.IF, yyline, yycolumn,yytext());}
<YYINITIAL> {ELSEIF}        {return new Symbol(sym.ELSEIF, yyline, yycolumn,yytext());}
<YYINITIAL> {ELSE}          {return new Symbol(sym.ELSE, yyline, yycolumn,yytext());}
<YYINITIAL> {MATCH}         {return new Symbol(sym.MATCH, yyline, yycolumn,yytext());}
<YYINITIAL> {WHILE}         {return new Symbol(sym.WHILE, yyline, yycolumn,yytext());}
<YYINITIAL> {FOR}           {return new Symbol(sym.FOR, yyline, yycolumn,yytext());}
<YYINITIAL> {DO}            {return new Symbol(sym.DO, yyline, yycolumn,yytext());}
<YYINITIAL> {BREAK}         {return new Symbol(sym.BREAK, yyline, yycolumn,yytext());}
<YYINITIAL> {CONTINUE}      {return new Symbol(sym.CONTINUE, yyline, yycolumn,yytext());}
<YYINITIAL> {PRINTLN}       {return new Symbol(sym.PRINTLN, yyline, yycolumn,yytext());}

//Expresiones regulares
<YYINITIAL> {BLANCOS}       {}
<YYINITIAL> {ID}            {return new Symbol(sym.ID, yyline, yycolumn,yytext());}
<YYINITIAL> {CADENA}        {return new Symbol(sym.CADENA, yyline, yycolumn,yytext());}
<YYINITIAL> {CHAR}          {return new Symbol(sym.CHAR, yyline, yycolumn,yytext());}
<YYINITIAL> {ENTERO}        {return new Symbol(sym.ENTERO, yyline, yycolumn,yytext());}
<YYINITIAL> {DECIMAL}       {return new Symbol(sym.DECIMAL, yyline, yycolumn,yytext());}

// estado cadena
<YYINITIAL> [\"]        {cadena = ""; yybegin(CADENA);}

<CADENA> {
    [\"]    {String tmp= cadena;
            cadena="";
            yybegin(YYINITIAL);
            return new Symbol(sym.CADENA, yyline, yycolumn,tmp);}
    
    [^\"]   {cadena+=yytext();}
}