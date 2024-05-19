/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastroeleitoral;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;


/**
 *
 * @author flaviorgs
 */
public class Pessoa {
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String cidade;
    private String estado;


    public Pessoa(String nome, String cpf, String dataNascimento, String cidade, String estado) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.cidade = cidade;
        this.estado = estado;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

   
    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    public List<String> getAtributos() {
        return Stream.of(nome, cpf, dataNascimento, cidade, estado )
                .flatMap(str -> Arrays.stream(str.split("\\s+")))
                .collect(Collectors.toList());
    }


    
    @Override
    public String toString() {
         return "Nome " + this.nome + "\nCPF: " + this.cpf +"\nDN: "+ this.dataNascimento + 
                 "\nEndereço:"+ this.cidade + " - "+ this.estado ;
        
    }
    


    
    
    
}
