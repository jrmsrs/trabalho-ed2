package tests;

import estruturas.ArvoreBB;
import estruturas.ArvoreAVL;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;

public class Tarefa2 {
  public static void main(String[] args) {
    Random gerador = new Random();

    String dirName = "";
    // itera sobre os diretorios de src/tests e cria o próximo diretório
    for (int i = 1; i <= 100 && dirName == ""; i++) {
      String dir = "./resultados/tarefa2/teste" + i;
      try {
        Files.createDirectory(Paths.get(dir));
        dirName = dir;
      } catch (IOException e) {
      }
    }

    int N = 100;
    while (N <= 10000) {
      ArvoreBB<Integer> arvoreBB = new ArvoreBB<>(); // Árvore binária de busca
      ArvoreAVL<Integer> arvoreAVL = new ArvoreAVL<>(); // Árvore AVL

      // Construir árvore binária de busca aleatória de tamanho N
      for (int i = 0; i < N; i++) {
        int valor = gerador.nextInt(1000); // Valores entre 0 e 999
        arvoreBB.inserir(valor);
        arvoreAVL.inserir(valor);
      }

      // Executar o loop para inserir e remover chaves aleatórias
      for (int i = 0; i < N * N; i++) {
        System.out.print("inserindo/removendo- " + (int) ((i / (double) (N * N)) * 100) + "%\r");
        int valorRemover = gerador.nextInt(1000); // Valor a ser removido entre 0 e 999
        int valorInserir = gerador.nextInt(1000); // Valor a ser inserido entre 0 e 999

        arvoreBB.remover(valorRemover);
        arvoreAVL.remover(valorRemover);

        arvoreBB.inserir(valorInserir);
        arvoreAVL.inserir(valorInserir);
      }

      salvaNoArquivo(dirName, "amostra", String.valueOf(N));
      salvaNoArquivo(dirName, "alturasBB", String.valueOf(arvoreBB.getAltura()));
      salvaNoArquivo(dirName, "alturasAVL", String.valueOf(arvoreAVL.getAltura()));

      System.out.println("\nN = " + N);
      System.out.println("Tamanho da árvore binária de busca: " + arvoreBB.getAltura());
      System.out.println("Tamanho da árvore AVL: " + arvoreAVL.getAltura());

      // Limpar as árvores para o próximo experimento
      arvoreBB.limpar();
      arvoreAVL.limpar();

      N = (int) Math.ceil(N * 1.2);
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
