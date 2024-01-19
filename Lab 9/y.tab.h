/* A Bison parser, made by GNU Bison 2.7.  */

/* Bison interface for Yacc-like parsers in C
   
      Copyright (C) 1984, 1989-1990, 2000-2012 Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

#ifndef YY_YY_Y_TAB_H_INCLUDED
# define YY_YY_Y_TAB_H_INCLUDED
/* Enabling traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     programa = 258,
     finprograma = 259,
     listo = 260,
     leer = 261,
     variable = 262,
     entero = 263,
     doble = 264,
     booleano = 265,
     chaar = 266,
     fi = 267,
     filse = 268,
     lse = 269,
     fiend = 270,
     entonces = 271,
     iniciobucle = 272,
     finbucle = 273,
     desde = 274,
     hasta = 275,
     imprime = 276,
     devolver = 277,
     romper = 278,
     and = 279,
     or = 280,
     IDENTIFIER = 281,
     CONSTANT = 282,
     SEMI_COLON = 283,
     COMA = 284,
     DOT = 285,
     PLUS = 286,
     MINUS = 287,
     MULTIPLY = 288,
     DIVISION = 289,
     MODULO = 290,
     LEFT_ROUND_BRACKETS = 291,
     RIGHT_ROUND_BRACKETS = 292,
     LEFT_SQUARE_BRACKETS = 293,
     RIGHT_SQUARE_BRACKETS = 294,
     LEFT_CURLY_BRACKETS = 295,
     RIGHT_CURLY_BRACKETS = 296,
     QUESTION_MARK = 297,
     LESS_THAN = 298,
     GREATER_THAN = 299,
     LESS_OR_EQUAL_THAN = 300,
     GREATER_OR_EQUAL_THAN = 301,
     DIFFERENT = 302,
     EQUAL = 303,
     ASSIGNMENT = 304,
     AND_OPERATOR = 305,
     OR_OPERATOR = 306
   };
#endif
/* Tokens.  */
#define programa 258
#define finprograma 259
#define listo 260
#define leer 261
#define variable 262
#define entero 263
#define doble 264
#define booleano 265
#define chaar 266
#define fi 267
#define filse 268
#define lse 269
#define fiend 270
#define entonces 271
#define iniciobucle 272
#define finbucle 273
#define desde 274
#define hasta 275
#define imprime 276
#define devolver 277
#define romper 278
#define and 279
#define or 280
#define IDENTIFIER 281
#define CONSTANT 282
#define SEMI_COLON 283
#define COMA 284
#define DOT 285
#define PLUS 286
#define MINUS 287
#define MULTIPLY 288
#define DIVISION 289
#define MODULO 290
#define LEFT_ROUND_BRACKETS 291
#define RIGHT_ROUND_BRACKETS 292
#define LEFT_SQUARE_BRACKETS 293
#define RIGHT_SQUARE_BRACKETS 294
#define LEFT_CURLY_BRACKETS 295
#define RIGHT_CURLY_BRACKETS 296
#define QUESTION_MARK 297
#define LESS_THAN 298
#define GREATER_THAN 299
#define LESS_OR_EQUAL_THAN 300
#define GREATER_OR_EQUAL_THAN 301
#define DIFFERENT 302
#define EQUAL 303
#define ASSIGNMENT 304
#define AND_OPERATOR 305
#define OR_OPERATOR 306



#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif

extern YYSTYPE yylval;

#ifdef YYPARSE_PARAM
#if defined __STDC__ || defined __cplusplus
int yyparse (void *YYPARSE_PARAM);
#else
int yyparse ();
#endif
#else /* ! YYPARSE_PARAM */
#if defined __STDC__ || defined __cplusplus
int yyparse (void);
#else
int yyparse ();
#endif
#endif /* ! YYPARSE_PARAM */

#endif /* !YY_YY_Y_TAB_H_INCLUDED  */
