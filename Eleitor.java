/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastroeleitoral;

/**
 *
 * @author flaviorgs
 */
public class Eleitor extends Pessoa {
    
    private int zonaEleitoral;
    private String titulo;
    int idade;

    public Eleitor(String nome, String cpf, String dataNascimento, String cidade, String estado, String titulo, int zonaEleitoral, int idade) {
        super(nome, cpf, dataNascimento, cidade, estado);
        this.zonaEleitoral = zonaEleitoral;
        this.titulo = titulo;
        this.idade = idade;
    }

    @Override
    public String toString() {
        return  "\nEleitor: " + this.getNome() +
                "\nTítulo de Eleitor: "+titulo+
                "\nIdade: "+ idade +
                "\nCidade: "+this.getCidade()+ "-"+this.getEstado()+
                "\nZona Eleitoral: "+zonaEleitoral+
                "\n---------------------------------------------------";

    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getZonaEleitoral() {
        return zonaEleitoral;
    }

    public void setZonaEleitoral(int zonaEleitoral) {
        this.zonaEleitoral = zonaEleitoral;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    
    
    
    
    
}
