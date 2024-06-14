package analisis;

//importaciones
import java_cup.runtime.Symbol;
import java.util.LinkedList;
import excepciones.Errores;

%%

//codigo de usuario
%{    
    public LinkedList<Errores> listaErrores = new LinkedList<>();
%}

%init{
    yyline = 1;
    yycolumn = 1;
    listaErrores = new LinkedList<>();
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
TKNOT = "!"
IGUAL = "="
IGUALACION = "=="
DIFERENCIA = "!="
MENORIGUAL = "<="
MENOR = "<"
MAYORIGUAL = ">="
MAYOR = ">"
TKOR = "||"
TKAND = "&&"
TKXOR = "^"
DOSPUNTOS = ":"

//palabras reservadas
PRINTLN = "println"
VERDAD = "TRUE"
FALSO = "false"
TKINT = "INT"
TKDOUBLE = "DOUBLE"
TKCHAR = "CHAR"
TKSTRING = "STRING"
TKBOOL = "BOOL"
CONST = "CONST"
VAR = "VAR"

//Expresiones regulares
ID=[a-zA-z][a-zA-Z0-9_]*
BLANCOS=[\ \r\t\f\n]+
ENTERO=[0-9]+
DECIMAL=[0-9]+"."[0-9]+
CADENA = [\"]([^\"])*[\"]
CARACTER = ['](([\\][rtfn])|([\\][u]([0-9a-fA-F]{4}))|([^\"]))[']
COMENTARIO = [/][/][^\n]*
COMENTARIOMULTI = [/][*][^*]*[*]+([^*/][^*]*[*]+)*[/]

%%

//Simbolos
<YYINITIAL> {FINCADENA}     {return new Symbol(sym.FINCADENA, yyline, yycolumn,yytext());}
<YYINITIAL> {PAR1}          {return new Symbol(sym.PAR1, yyline, yycolumn,yytext());}
<YYINITIAL> {PAR2}          {return new Symbol(sym.PAR2, yyline, yycolumn,yytext());}
<YYINITIAL> {DOSPUNTOS}     {return new Symbol(sym.DOSPUNTOS, yyline, yycolumn,yytext());}

<YYINITIAL> {MAS}           {return new Symbol(sym.MAS, yyline, yycolumn,yytext());}
<YYINITIAL> {MENOS}         {return new Symbol(sym.MENOS, yyline, yycolumn,yytext());}
<YYINITIAL> {POTENCIA}      {return new Symbol(sym.POTENCIA, yyline, yycolumn,yytext());}
<YYINITIAL> {MULT}          {return new Symbol(sym.MULT, yyline, yycolumn,yytext());}
<YYINITIAL> {DIV}           {return new Symbol(sym.DIV, yyline, yycolumn,yytext());}
<YYINITIAL> {MOD}           {return new Symbol(sym.MOD, yyline, yycolumn,yytext());}
<YYINITIAL> {IGUAL}         {return new Symbol(sym.IGUAL, yyline, yycolumn,yytext());}

<YYINITIAL> {TKNOT}         {return new Symbol(sym.TKNOT, yyline, yycolumn,yytext());}
<YYINITIAL> {IGUALACION}    {return new Symbol(sym.IGUALACION, yyline, yycolumn,yytext());}
<YYINITIAL> {DIFERENCIA}    {return new Symbol(sym.DIFERENCIA, yyline, yycolumn,yytext());}
<YYINITIAL> {MENORIGUAL}    {return new Symbol(sym.MENORIGUAL, yyline, yycolumn,yytext());}
<YYINITIAL> {MENOR}         {return new Symbol(sym.MENOR, yyline, yycolumn,yytext());}
<YYINITIAL> {MAYORIGUAL}    {return new Symbol(sym.MAYORIGUAL, yyline, yycolumn,yytext());}
<YYINITIAL> {MAYOR}         {return new Symbol(sym.MAYOR, yyline, yycolumn,yytext());}
<YYINITIAL> {TKOR}          {return new Symbol(sym.TKOR, yyline, yycolumn,yytext());}
<YYINITIAL> {TKAND}         {return new Symbol(sym.TKAND, yyline, yycolumn,yytext());}
<YYINITIAL> {TKXOR}         {return new Symbol(sym.TKXOR, yyline, yycolumn,yytext());}


//Palabras reservadas
<YYINITIAL> {PRINTLN}       {return new Symbol(sym.PRINTLN, yyline, yycolumn,yytext());}
<YYINITIAL> {VERDAD}        {return new Symbol(sym.VERDAD, yyline, yycolumn,yytext());}
<YYINITIAL> {FALSO}         {return new Symbol(sym.FALSO, yyline, yycolumn,yytext());}
<YYINITIAL> {TKINT}         {return new Symbol(sym.TKINT, yyline, yycolumn,yytext());}
<YYINITIAL> {TKDOUBLE}      {return new Symbol(sym.TKDOUBLE, yyline, yycolumn,yytext());}
<YYINITIAL> {TKCHAR}        {return new Symbol(sym.TKCHAR, yyline, yycolumn,yytext());}
<YYINITIAL> {TKSTRING}      {return new Symbol(sym.TKSTRING, yyline, yycolumn,yytext());}
<YYINITIAL> {TKBOOL}        {return new Symbol(sym.TKBOOL, yyline, yycolumn,yytext());}
<YYINITIAL> {CONST}         {return new Symbol(sym.CONST, yyline, yycolumn,yytext());}
<YYINITIAL> {VAR}           {return new Symbol(sym.VAR, yyline, yycolumn,yytext());}

<YYINITIAL> {ID}            {return new Symbol(sym.ID, yyline, yycolumn,yytext());}

//Expresiones regulares
<YYINITIAL> {DECIMAL}   {return new Symbol(sym.DECIMAL, yyline, yycolumn,yytext());}
<YYINITIAL> {ENTERO}    {return new Symbol(sym.ENTERO, yyline, yycolumn,yytext());}
<YYINITIAL> {CADENA}    {
                            String cadena = yytext();
                            cadena = cadena.substring(1, cadena.length()-1);
                            int find = -1;
                            
                            find = cadena.indexOf("\\n"); 
                            if (find != -1) {
                                cadena = cadena.replace("\\n", "\n");
                            }

                            find = cadena.indexOf("\\t"); 
                            if (find != -1) {
                                cadena = cadena.replace("\\t", "\t");
                            }

                            find = cadena.indexOf("\\r"); 
                            if (find != -1) {
                                cadena = cadena.replace("\\r", "\r");
                            }

                            find = cadena.indexOf("\\f"); 
                            if (find != -1) {
                                cadena = cadena.replace("\\f", "\f");
                            }
                            
                            return new Symbol(sym.CADENA, yyline, yycolumn,cadena);
                        }
<YYINITIAL> {CARACTER} {
                            String caracter = yytext();
                            caracter = caracter.substring(1, caracter.length()-1);
                            if (caracter.equals("\\n")) {
                                caracter = "\n";
                            }
                            if (caracter.equals("\\r")) {
                                caracter = "\r";
                            }
                            if (caracter.equals("\\t")) {
                                caracter = "\t";
                            }
                            if (caracter.equals("\\f")) {
                                caracter = "\f";
                            }
                            return new Symbol(sym.CARACTER, yyline, yycolumn, caracter);
                        }


<YYINITIAL> {BLANCOS} {}

//comentarios
//Una linea
<YYINITIAL> {COMENTARIO} {}
//multiLinea
<YYINITIAL> {COMENTARIOMULTI} {}


<YYINITIAL> . {
                listaErrores.add(new Errores("LEXICO","El caracter "+
                yytext()+" NO pertenece al lenguaje", yyline, yycolumn));
}
