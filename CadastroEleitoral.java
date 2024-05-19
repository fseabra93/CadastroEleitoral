/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cadastroeleitoral;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 *
 * @author flaviorgs
 */
public class CadastroEleitoral {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        List<Pessoa> pessoas = new ArrayList<>();
        
        Map<String, Eleitor> eleitores = new HashMap<>();        
        
        pessoas.add(new Pessoa("Harry Potter", "838.084.030-55", "31/07/1980", "Natal", "RN"));
        pessoas.add(new Pessoa("Walter White", "689111720-81", "07/09/1958", "Parnamirim", "RN"));
        pessoas.add(new Pessoa("Harvey Specter", "976.009.930-66", "22/01/1972", "Parnamirim", "RN"));
        pessoas.add(new Pessoa("Elaine Bennes", "859.273.820-29", "14/05/1975", "Macaiba", "RN"));
        pessoas.add(new Pessoa("Hermione Granger", "67798083006", "19/09/1979", "Natal", "RN"));
        pessoas.add(new Pessoa("Chandler Bing", "151.285.97024", "18/10/1967", "Parnamirim", "RN"));
        pessoas.add(new Pessoa("Sheldon Cooper", "151.285.970-23", "26/02/1980", "Natal", "RN")); //CPF inválido
        pessoas.add(new Pessoa("Phoebe Buffay", "207302.750-43", "16/02/1967", "Macaiba", "RN"));
        pessoas.add(new Pessoa("Beth Harmon", "151.285.970-24", "12/05/1979", "Natal", "RN")); //CPF igual ao de Chandler Bing
        pessoas.add(new Pessoa("Bartholomew Simpson", "999.493.460-02", "28/09/2010", "Natal", "RN")); //menos de 16 anos


        pessoas.forEach((Pessoa p)->{
            
            int idade = checarIdade(p.getDataNascimento());
            if (idade > 15){
                
                List<Character> cpf_padronizado = padronizaCPF(p.getCpf());

                boolean cpf_check = checarCPF(cpf_padronizado);

                if (cpf_check){
                    String cpf_reconstruido = reconstroiCPF(cpf_padronizado);
                    boolean achouCpf = eleitores.keySet().stream()
                        .anyMatch(cpf -> cpf.equals(cpf_reconstruido));

                    if (achouCpf == false){
                        String titulo = geradorDeTitulo();
                        int num_zona = selecionaZona(p.getCidade());
                        eleitores.put(cpf_reconstruido, new Eleitor(p.getNome(), cpf_reconstruido, p.getDataNascimento(), p.getCidade(), p.getEstado(), titulo, num_zona, idade));  

                    } else {
                        System.out.println("Não foi atribuído título de eleitor para " + p.getNome() + " porque o CPF digitado: " + p.getCpf() + " já se encontra na nossa base de dados.");
                    }

                } else {
                    System.out.println("Não foi atribuído título de eleitor para " + p.getNome() + " porque o CPF digitado: " + p.getCpf() + " é inválido");
                }

                cpf_padronizado.clear();
                
            } else {
                System.out.println(p.getNome()+ " ainda não tem a idade mínima para votar. Poderá se cadastrar daqui a "+ (16-idade) + " anos");
            }

        });
        
        System.out.println("\nLista de eleitores.\n------------------------------");
        eleitores.forEach((cpf, eleitor) -> 
            System.out.println("CPF: " + cpf + eleitor)
        );
        
               //////////////////////////////////////////////////
        
        Map <Integer, Set<String>> mapaPorZona = eleitores.values().stream()
                    .collect(Collectors.groupingBy(   
                        (Eleitor e)-> e.getZonaEleitoral(),
                        TreeMap::new,
                        Collectors.mapping(
                                (Eleitor e)->e.getNome(), 
                                Collectors.toSet())
                    ));

        

        System.out.println("\nImprimindo a lista de eleitores agrupados por Zona Eleitoral usando groupingBy e mapping.");
        mapaPorZona.forEach((zona, nomes) -> 
            System.out.println("Eleitores da Zona Eleitoral " + zona + ": "+nomes)
        );
       
    }

    
    /////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static boolean checarCPF(List<Character> cpf_checar){
        
        List<Character> elementos_principais = cpf_checar.stream()
                        .limit(9)
                        .collect(Collectors.toList());
        
          
        List<Character> elementos_verificadores = cpf_checar.stream()
                        .skip(9)
                        .collect(Collectors.toList());
        
        int primeiroVerificador = Character.getNumericValue(elementos_verificadores.get(0));
        int segundoVerificador = Character.getNumericValue(elementos_verificadores.get(1));
        
        boolean prim_digit_cpf = checa_primeiro_digito(elementos_principais, primeiroVerificador);
        boolean seg_digit_cpf = checa_segundo_digito(elementos_principais, primeiroVerificador, segundoVerificador);
        
        if (prim_digit_cpf && seg_digit_cpf){
            return true;
        } else {
            return false;
        }


    }
    

    
    public static boolean checa_primeiro_digito(List<Character>lista, int verificador){
        int mult = 10;
        List<Integer>lista_multiplica = new ArrayList();
        
        for (char valor : lista ){
            lista_multiplica.add(mult*(Character.getNumericValue(valor)));
            mult--;
        }
        
        int soma = lista_multiplica.stream()
                .reduce(0, Integer::sum);
        
        int resto = soma % 11;
        
        if ((resto == 0 || resto == 1) && verificador == 0){
            return true;
        } else if ((resto != 0 && resto != 1) && (11 - resto == verificador) ){
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean checa_segundo_digito(List<Character>lista, int num , int verificador){
        int mult = 11;
        List<Integer>lista_multiplica = new ArrayList();
        
        for (char valor : lista ){
            lista_multiplica.add(mult*(Character.getNumericValue(valor)));
            mult--;
        }
        
        lista_multiplica.add(num*2);
        
        int soma = lista_multiplica.stream()
                .reduce(0, Integer::sum);
        
        int resto = soma % 11;
                
        if ((resto == 0 || resto == 1) && verificador == 0){
            return true;
        } else if ((resto != 0 && resto != 1) && (11 - resto == verificador) ){
            return true;
        } else {
            return false;
        }
        
    }
    
    public static String reconstroiCPF(List<Character>lista){
        String cpf_primeiraParte = lista.stream()
                .map(String::valueOf)
                .limit(3)
                .collect(Collectors.joining());
        
        String cpf_segundaParte = lista.stream()
                .map(String::valueOf)
                .limit(6)
                .skip(3)
                .collect(Collectors.joining());
        
        String cpf_terceiraParte = lista.stream()
                .map(String::valueOf)
                .limit(9)
                .skip(6)
                .collect(Collectors.joining());
        
        String cpf_verificadores = lista.stream()
                .map(String::valueOf)
                .skip(9)
                .collect(Collectors.joining());
        
        String concatenacao1 = Stream.of(cpf_primeiraParte, cpf_segundaParte, cpf_terceiraParte)
                .collect(Collectors.joining("."));
        
        String concatenacao_final = Stream.of(concatenacao1, cpf_verificadores)
                .collect(Collectors.joining("-"));
        

        return concatenacao_final;
        
    }
    
    public static String geradorDeTitulo(){
       
        Supplier<Double> fabricaDeNumeros = () -> Math.random();
        int parte1num = (int)(fabricaDeNumeros.get()*10000);
        String parte1str = String.valueOf(parte1num);
        int parte2num = (int)(fabricaDeNumeros.get()*10000);
        String parte2str = String.valueOf(parte2num);
        int parte3num = (int)(fabricaDeNumeros.get()*10000);
        String parte3str = String.valueOf(parte3num);
        
        List<String> titulo_completo = new ArrayList<>();
        titulo_completo.add(parte1str);
        titulo_completo.add(parte2str);
        titulo_completo.add(parte3str);
        
        //colocar zeros se a parte gerada do título de eleitor for menor que 1000
        
        int cont = 0;
        for (String parte : titulo_completo){
            int tam = parte.length();
            if (tam < 4){
                for (int i = 0; i < (4-tam); i++){
                    parte = "0" + parte;
                }
                titulo_completo.set(cont, parte);  
            }
            cont++;
        }

        String titulo = Stream.of(titulo_completo.get(0), titulo_completo.get(1), titulo_completo.get(2))
                .collect(Collectors.joining(" "));
        
        
        return titulo;
        
    }
    
    
    public static int checarIdade(String dataString){
        LocalDate dataNascimento = LocalDate.parse(dataString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate dataAtual = LocalDate.now();
        Period periodo = Period.between(dataNascimento, dataAtual);
        int idade = periodo.getYears();
        
        return idade;
        

    }
    
    
    public static int selecionaZona(String cidade){
        
        Map<String, Integer> mapCidades = new HashMap<>();

        mapCidades.put("Natal", 10);
        mapCidades.put("Parnamirim", 20);
        mapCidades.put("Macaiba", 30);
    
        return mapCidades.get(cidade);
               
    }
    
    public static List<Character> padronizaCPF(String cpf_padronizar){
        List<Character> elementos = cpf_padronizar.chars() 
              .mapToObj(c -> (char) c) 
              .collect(Collectors.toList()); 

        elementos.removeIf(c -> !Character.isDigit(c));

        return elementos;
        
        
    }
    
}
