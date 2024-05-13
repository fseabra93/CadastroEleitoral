/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cadastroeleitoral;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Cpfs<Integer> lista_de_cpfs = new Cpfs<>();
        List<Eleitor> eleitores = new ArrayList<>();
        
        
        pessoas.add(new Pessoa("Robert De Niro", "090.088.774-57", "17/08/1943", "rua não sei o que", "23", "Lagoa Nova", "59075810", "Natal", "RN"));
        pessoas.add(new Pessoa("Fulano", "090.088.774-57", "17/08/1943", "rua nãoseioque", "23", "Lagoa Nova", "59075810", "Parnamirim", "RN"));

        pessoas.forEach((Pessoa p)->{
            int atribuir_titulo = 0;
            p.getAtributos();
            List<String> atributos = p.getAtributos();
            List<String> stringsComNumeros = atributos.stream()
                .filter(s -> s.startsWith("0") || s.startsWith("1") || s.startsWith("2") || s.startsWith("3") || s.startsWith("4") || s.startsWith("5") || s.startsWith("6") || s.startsWith("7") || s.startsWith("8") || s.startsWith("9"))
                .collect(Collectors.toList());
            
            List<Integer> posicoes = IntStream.range(0, atributos.size())
                .filter(i -> stringsComNumeros.contains(atributos.get(i)))
                .boxed()
                .collect(Collectors.toList());
            
            int posicaoCPF = posicoes.get(0);
            int posicaoDN = posicoes.get(1);
            int posicaoNumCasa = posicoes.get(2);
            int posicaoCEP = posicoes.get(3);
            int posicaoCidade = posicaoCEP +1;
            
            
            String nome = atributos.stream()
                .limit(posicaoCPF)
                .collect(Collectors.joining(" "));
            
            int cpf_num = checarCPF(atributos.get(posicaoCPF));
            if (cpf_num > 0){
                atribuir_titulo = lista_de_cpfs.adicionar(cpf_num);
            }
                    
            int idade = checarIdade(atributos.get(posicaoDN));
            
            //////////unir nome da rua e do bairro
            String nome_da_rua = unirNomeDaRuaeBairro(atributos, posicaoDN, posicaoNumCasa);
            String nome_do_bairro = unirNomeDaRuaeBairro(atributos, posicaoNumCasa, posicaoCEP);
            
            ///// selecionar Zona Eleitoral
            int num_zona = selecionaZona(atributos.get(posicaoCidade));
             
            
            System.out.println("Nome de rua:"+ nome_da_rua);
            System.out.println("Nome do bairro:"+ nome_do_bairro);
            System.out.println("ZOna:"+ num_zona);
            
            
           // Obs. AGORA SÓ FALTA INSERIR NA CLASSE ELEITORES
            
        
           /* if (atribuir_titulo > 0){
                String titulo = geradorDeTitulo();
                eleitores.add(nome, atributos.get(posicaoCPF), atributos.get(posicaoCPF)+1, endereco_rua, endereco_num, endereco_bairro, endereco_cep, cidade, estado);
                System.out.println("Nome: " + nome + "\nCPF: " + atributos.get(posicaoCPF) + "\nTítulo Eleitoral: " + titulo);
            }*/
            
        });
       
        
        lista_de_cpfs.imprimir();
        
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static int checarCPF(String cpf_checar){
        
        List<Character> elementos = cpf_checar.chars() 
              .mapToObj(c -> (char) c) 
              .collect(Collectors.toList()); 

        elementos.removeIf(c -> !Character.isDigit(c));
        
        List<Integer> elementos_num = elementos.stream()
                .map(Character::getNumericValue)
                .collect(Collectors.toList());
        
        boolean prim_digit_cpf = checa_primeiro_digito(elementos_num);
        boolean seg_digit_cpf = checa_segundo_digito(elementos_num);
        
        if (prim_digit_cpf && seg_digit_cpf){
            System.out.println("CPF Válido");
            int cpf_num = reconstroiCPF(elementos_num);
            return cpf_num;
        } else {
            System.out.println("CPF inválido");
            return -1;
        }


    }
    
    public static boolean checa_primeiro_digito(List<Integer>lista){
                int mult = 10;
        List<Integer>lista_multiplica = new ArrayList();
        for (int num : lista ){
            lista_multiplica.add(num*mult);
            mult--;
        }
        
        List<Integer>lista_multiplica_final = lista_multiplica.stream()
                .limit(9)
                .collect(Collectors.toList());
        
        List<Integer> digitos_verificadores = lista.stream()
                .skip(lista.size() - 2)
                .collect(Collectors.toList());
        
        int soma = lista_multiplica_final.stream()
                .reduce(0, Integer::sum);
        
        int resto = soma % 11;
        
        if ((resto == 0 || resto == 1) && digitos_verificadores.get(0) == 0){
            return true;
        } else if ((resto != 0 && resto != 1) && (11 - resto == digitos_verificadores.get(0)) ){
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean checa_segundo_digito(List<Integer>lista){
        int mult = 11;
        List<Integer>lista_multiplica = new ArrayList();
        for (int num : lista ){
            lista_multiplica.add(num*mult);
            mult--;
        }
        
        List<Integer>lista_multiplica_final = lista_multiplica.stream()
                .limit(10)
                .collect(Collectors.toList());
        
        List<Integer> digitos_verificadores = lista.stream()
                .skip(lista.size() - 1)
                .collect(Collectors.toList());
        
        int soma = lista_multiplica_final.stream()
                .reduce(0, Integer::sum);
        
        int resto = soma % 11;
                
        if ((resto == 0 || resto == 1) && digitos_verificadores.get(0) == 0){
            return true;
        } else if ((resto != 0 && resto != 1) && (11 - resto == digitos_verificadores.get(0)) ){
            return true;
        } else {
            return false;
        }
        
    }
    
    public static int reconstroiCPF(List<Integer>lista){
        
        int cpf_num = lista.stream()
                .limit(9)
                .reduce(0, (acc, digit) -> acc * 10 + digit);

        return cpf_num;
        
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
                    
        String titulo = titulo_completo.get(0) + " " + titulo_completo.get(1) + " " + titulo_completo.get(2);
        
        return titulo;
        
    }
    
    
    public static int checarIdade(String dataString){
        LocalDate dataNascimento = LocalDate.parse(dataString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate dataAtual = LocalDate.now();
        Period periodo = Period.between(dataNascimento, dataAtual);
        int idade = periodo.getYears(); // Obter idade em anos
        
        return idade;
        

    }
    
    public static String unirNomeDaRuaeBairro(List<String>lista, int posicao1, int posicao2){
                        
            int posicao_inicio_rua = posicao1 + 1;
            int tam_NomeRua = posicao2 - posicao_inicio_rua;
            
            List<String> nome_rua = new ArrayList<>();
            while (tam_NomeRua > 0){
                nome_rua.add(lista.get(posicao_inicio_rua));
                posicao_inicio_rua++;
                tam_NomeRua--;
            }
            Stream<String> streamStrings = nome_rua.stream();
            String nome_unido = streamStrings
                .collect(Collectors.joining(" "));
            
            return nome_unido;
    }
    
    public static int selecionaZona(String cidade){
        
        Map<String, Integer> mapCidades = new HashMap<>();

        // Popular o Map com chave e valor int
        mapCidades.put("Natal", 10);
        mapCidades.put("Parnamirim", 20);
        mapCidades.put("Macaiba", 30);
        
        Integer num = mapCidades.get(cidade);
        return num;
               
    }
    
}
