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
    private String endereco_rua;
    private String endereco_num;
    private String endereco_bairro;
    private String endereco_cep;
    private String cidade;
    private String estado;


    public Pessoa(String nome, String cpf, String dataNascimento, String endereco_rua, String endereco_num, String endereco_bairro, String endereco_cep, String cidade, String estado) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.endereco_rua = endereco_rua;
        this.endereco_num = endereco_num;
        this.endereco_bairro = endereco_bairro;
        this.endereco_cep = endereco_cep;
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

    public String getEndereco_rua() {
        return endereco_rua;
    }

    public void setEndereco_rua(String endereco_rua) {
        this.endereco_rua = endereco_rua;
    }

    public String getEndereco_num() {
        return endereco_num;
    }

    public void setEndereco_num(String endereco_num) {
        this.endereco_num = endereco_num;
    }

    public String getEndereco_bairro() {
        return endereco_bairro;
    }

    public void setEndereco_bairro(String endereco_bairro) {
        this.endereco_bairro = endereco_bairro;
    }

    public String getEndereco_cep() {
        return endereco_cep;
    }

    public void setEndereco_cep(String endereco_cep) {
        this.endereco_cep = endereco_cep;
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
        return Stream.of(nome, cpf, dataNascimento, endereco_rua, endereco_num, endereco_bairro, endereco_cep, cidade, estado )
                .flatMap(str -> Arrays.stream(str.split("\\s+")))
                .collect(Collectors.toList());
    }


    
    @Override
    public String toString() {
         return "Nome " + this.nome + "\nCPF: " + this.cpf +"\nDN: "+ this.dataNascimento + 
                 "\nEndereço:" + this.endereco_rua + 
                 ", " + this.endereco_num + ", " + this.endereco_bairro + "\nCEP: "
                 + this.endereco_cep + "\n" + this.cidade + " - "+ this.estado ;
        
    }
    


    
    
    
}
