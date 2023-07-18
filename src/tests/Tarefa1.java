package tests;

import estruturas.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Tarefa1 {
  public static void main(String[] args) {
    Map<Integer, List<Integer>> mapaAlturasBB = new HashMap<>();
    Map<Integer, List<Integer>> mapaAlturasAVL = new HashMap<>();
    Map<Integer, List<Integer>> mapaAlturasRN = new HashMap<>();
    Random geradorComSemente = new Random(0);
    Random gerador = new Random(); // Semente aleatória

    String dirName = "";
    // itera sobre os diretorios de src/tests e cria o próximo diretório
    for (int i = 1; i <= 100 && dirName == ""; i++) {
      String dir = "./resultados/tarefa1/teste" + i;
      try {
        Files.createDirectory(Paths.get(dir));
        dirName = dir;
      } catch (IOException e) {
      }
    }

    for (int i = 0; i < 100; i++) {
      System.out.print(i + "%\r");
      int amostra = geradorComSemente.nextInt(990000) + 10000; // entre 10.000 e 1.000.000

      ArvoreBB<Float> arvoreBB = new ArvoreBB<>();
      ArvoreAVL<Float> arvoreAVL = new ArvoreAVL<>();
      ArvoreRN<Float> arvoreRN = new ArvoreRN<>();

      for (int j = 0; j < amostra; j++) {
        float valor = gerador.nextFloat() * 100;
        arvoreBB.inserir(valor);
        arvoreAVL.inserir(valor);
        arvoreRN.inserir(valor);
      }

      int alturaBB = arvoreBB.getAltura();
      int alturaAVL = arvoreAVL.getAltura();
      int alturaRN = arvoreRN.getAltura();

      if (!mapaAlturasBB.containsKey(amostra)) {
        mapaAlturasBB.put(amostra, new ArrayList<>());
      }
      if (!mapaAlturasAVL.containsKey(amostra)) {
        mapaAlturasAVL.put(amostra, new ArrayList<>());
      }
      if (!mapaAlturasRN.containsKey(amostra)) {
        mapaAlturasRN.put(amostra, new ArrayList<>());
      }

      mapaAlturasBB.get(amostra).add(alturaBB);
      mapaAlturasAVL.get(amostra).add(alturaAVL);
      mapaAlturasRN.get(amostra).add(alturaRN);

      arvoreBB.limpar();
      arvoreAVL.limpar();
      arvoreRN.limpar();
    }

    // Ordenar os valores pelo número de amostras e altura da árvore
    List<Map.Entry<Integer, List<Integer>>> listaOrdenadaBB = new ArrayList<>(mapaAlturasBB.entrySet());
    List<Map.Entry<Integer, List<Integer>>> listaOrdenadaAVL = new ArrayList<>(mapaAlturasAVL.entrySet());
    List<Map.Entry<Integer, List<Integer>>> listaOrdenadaRN = new ArrayList<>(mapaAlturasRN.entrySet());

    listaOrdenadaBB.sort(Comparator.comparing((Map.Entry<Integer, List<Integer>> entry) -> entry.getKey()));
    listaOrdenadaAVL.sort(Comparator.comparing((Map.Entry<Integer, List<Integer>> entry) -> entry.getKey()));
    listaOrdenadaRN.sort(Comparator.comparing((Map.Entry<Integer, List<Integer>> entry) -> entry.getKey()));

    // Imprimir os valores ordenados
    for (int i = 0; i < listaOrdenadaBB.size(); i++) {
      Map.Entry<Integer, List<Integer>> entryBB = listaOrdenadaBB.get(i);
      Map.Entry<Integer, List<Integer>> entryAVL = listaOrdenadaAVL.get(i);
      Map.Entry<Integer, List<Integer>> entryRN = listaOrdenadaRN.get(i);

      int amostra = entryBB.getKey();
      List<Integer> alturasBB = entryBB.getValue();
      List<Integer> alturasAVL = entryAVL.getValue();
      List<Integer> alturasRN = entryRN.getValue();

      String alturaBB = alturasBB.toString();
      String alturaAVL = alturasAVL.toString();
      String alturaRN = alturasRN.toString();

      salvaNoArquivo(dirName, "amostra", String.valueOf(entryBB.getKey()));
      salvaNoArquivo(dirName, "alturasBB", alturaBB.substring(1, alturaBB.length() - 1));
      salvaNoArquivo(dirName, "alturasAVL", alturaAVL.substring(1, alturaAVL.length() - 1));
      salvaNoArquivo(dirName, "alturasRN", alturaRN.substring(1, alturaRN.length() - 1));

      System.out.println(amostra + " -> BB: " + alturasBB + " | AVL: " + alturasAVL + " | RN: " + alturasRN);
    }
  }

  static void salvaNoArquivo(String dirName, String fileName, String entrada) {
    String arquivo = dirName + "/" + fileName + ".txt";
    try {
      if (!Files.exists(Paths.get(arquivo)))
        Files.createFile(Paths.get(arquivo));
      Files.write(Paths.get(arquivo), (entrada + "\n").getBytes(), StandardOpenOption.APPEND);
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
