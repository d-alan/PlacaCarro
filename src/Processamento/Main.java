/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processamento;

import Catalano.Imaging.FastBitmap;

public class Main {
    
    public static void main (String[] args){
        
        FastBitmap fb = new FastBitmap("D:\\PDI\\PlacaCarro\\Placas\\placa2.jpg");
        ReconhecerPlaca rp = new ReconhecerPlaca();
        rp.aplicar(fb);
        
    }
    
}
