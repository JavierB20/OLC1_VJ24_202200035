package analisis;

//importaciones
import java_cup.runtime.Symbol;

%%

//codigo de usuario
%{

%}

%init{
    yyline = 1;
    yycolumn = 1;
%init}

//caracteristicas de jflex
%cup
%class scanner
%public
%line
%char
%column
%full
//%debug
%ignorecase

//simbolos del sistema
PAR1="("
PAR2=")"
MAS = "+"
MENOS = "-"
POTENCIA = "**"
MULT = "*"
DIV = "/"
MOD = "%"
FINCADENA=";"


//palabras reservadas
PRINTLN = "println"
VERDAD = "TRUE"
FALSO = "false"

//Expresiones regulares
BLANCOS=[\ \r\t\f\n]+
ENTERO=[0-9]+
DECIMAL=[0-9]+"."[0-9]+
CADENA = [\"]([^\"])*[\"]
CARACTER = [']([^\"])['']


%%
//Simbolos
<YYINITIAL> {FINCADENA} {return new Symbol(sym.FINCADENA, yyline, yycolumn,yytext());}
<YYINITIAL> {PAR1} {return new Symbol(sym.PAR1, yyline, yycolumn,yytext());}
<YYINITIAL> {PAR2} {return new Symbol(sym.PAR2, yyline, yycolumn,yytext());}

<YYINITIAL> {MAS} {return new Symbol(sym.MAS, yyline, yycolumn,yytext());}
<YYINITIAL> {MENOS} {return new Symbol(sym.MENOS, yyline, yycolumn,yytext());}
<YYINITIAL> {POTENCIA} {return new Symbol(sym.POTENCIA, yyline, yycolumn,yytext());}
<YYINITIAL> {MULT} {return new Symbol(sym.MULT, yyline, yycolumn,yytext());}
<YYINITIAL> {DIV} {return new Symbol(sym.DIV, yyline, yycolumn,yytext());}
<YYINITIAL> {MOD} {return new Symbol(sym.MOD, yyline, yycolumn,yytext());}


<YYINITIAL> {BLANCOS} {}

//Palabras reservadas
<YYINITIAL> {PRINTLN} {return new Symbol(sym.PRINTLN, yyline, yycolumn,yytext());}
<YYINITIAL> {VERDAD} {return new Symbol(sym.VERDAD, yyline, yycolumn,yytext());}
<YYINITIAL> {FALSO} {return new Symbol(sym.FALSO, yyline, yycolumn,yytext());}

//Expresiones regulares
<YYINITIAL> {DECIMAL} {return new Symbol(sym.DECIMAL, yyline, yycolumn,yytext());}
<YYINITIAL> {ENTERO} {return new Symbol(sym.ENTERO, yyline, yycolumn,yytext());}
<YYINITIAL> {CADENA} {
    String cadena = yytext();
    cadena = cadena.substring(1, cadena.length()-1);
    return new Symbol(sym.CADENA, yyline, yycolumn,cadena);
    }
<YYINITIAL> {CARACTER} {
    String caracter = yytext();
    caracter = caracter.substring(1, caracter.length()-1);
    return new Symbol(sym.CARACTER, yyline, yycolumn,caracter);
    }
