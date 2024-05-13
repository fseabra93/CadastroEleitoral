/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastroeleitoral;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author flaviorgs
 */
public class Cpfs<T> {
    
    private final List<T>lista_de_cpfs;
    
    public Cpfs() {
        this.lista_de_cpfs = new ArrayList<>();
    }

    public int adicionar(T cpf_num) {
        
        if (lista_de_cpfs.contains(cpf_num)) {
            System.out.println("O CPF: " + cpf_num + " já existe no cadastro. "
                    + "\nFavor checar erro de digitação ou duplicidade de registro de eleitor.");
            return -1;
        } else {
            lista_de_cpfs.add(cpf_num);
            return 1;
        }
        
    }
    
    public void imprimir() {
        System.out.println("Todos os CPFs: " + lista_de_cpfs);
    }
    
    
}
