/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package analizadorlexico.Models.analizador.lexico;

/**
 *
 * @author Israel Morales
 */
public enum BanderasAnalizadorLexico {
    NO_PERTENECE_AL_ALFABETO(-2),
    NO_ES_LEXEMA(-3),
    OMITIR_AFN(-4);

    private BanderasAnalizadorLexico() {
        this.valorBandera = 0;
    }

    private BanderasAnalizadorLexico(int valorBandera) {
        this.valorBandera = valorBandera;
    }

    public static BanderasAnalizadorLexico getNO_PERTENECE_AL_ALFABETO() {
        return NO_PERTENECE_AL_ALFABETO;
    }

    public static BanderasAnalizadorLexico getNO_ES_LEXEMA() {
        return NO_ES_LEXEMA;
    }

    public static BanderasAnalizadorLexico getOMITIR_AFN() {
        return OMITIR_AFN;
    }

    public int getValorBandera() {
        return valorBandera;
    }
    
    public final int valorBandera;
}
