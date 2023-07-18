package estruturas;

public class ArvoreAVL<T extends Comparable<T>> {
  class NoAVL {
    T dado;
    NoAVL esq;
    NoAVL dir;
    int altura;

    public NoAVL(T dado) {
      this.dado = dado;
      this.esq = this.dir = null;
    }

    public String imprime(int cor) {
      final String ANSI_RESET = "\u001B[0m";
      String ansiColor = "\u001B[" + (cor % 6 + 31) + "m";
      String str = ansiColor + "{ \"dado\": " + this.dado + ", " + ANSI_RESET;
      str += ansiColor + "\"esq\": " + (this.esq != null ? this.esq.imprime(cor + 1) : null) + ansiColor + ", "
          + ANSI_RESET;
      str += ansiColor + "\"dir\": " + (this.dir != null ? this.dir.imprime(cor + 1) : null) + ansiColor + " }"
          + ANSI_RESET;
      str += "(" + this.altura + ")";
      return str;
    }
  }

  protected NoAVL raiz;

  public ArvoreAVL() {
    this.raiz = null;
  }

  public void inserir(T dado) {
    this.raiz = inserir(this.raiz, dado);
  }

  protected NoAVL inserir(NoAVL no, T dado) {
    if (no == null) {
      return new NoAVL(dado);
    }
    if (dado.compareTo(no.dado) < 0) {
      no.esq = inserir(no.esq, dado);
    } else if (dado.compareTo(no.dado) > 0) {
      no.dir = inserir(no.dir, dado);
    } else {
      return no;
    }
    no.altura = 1 + Math.max(altura(no.esq), altura(no.dir));
    int balance = balanceamento(no);
    if (balance > 1 && dado.compareTo(no.esq.dado) < 0) {
      return rotacaoDir(no);
    }
    if (balance < -1 && dado.compareTo(no.dir.dado) > 0) {
      return rotacaoEsq(no);
    }
    if (balance > 1 && dado.compareTo(no.esq.dado) > 0) {
      no.esq = rotacaoEsq(no.esq);
      return rotacaoDir(no);
    }
    if (balance < -1 && dado.compareTo(no.dir.dado) < 0) {
      no.dir = rotacaoDir(no.dir);
      return rotacaoEsq(no);
    }
    return no;
  }

  protected int altura(NoAVL no) {
    if (no == null) {
      return 0;
    }
    return no.altura;
  }

  protected int balanceamento(NoAVL no) {
    if (no == null) {
      return 0;
    }
    return altura(no.esq) - altura(no.dir);
  }

  protected NoAVL rotacaoDir(NoAVL no) {
    NoAVL esq = no.esq;
    NoAVL dir = esq.dir;
    esq.dir = no;
    no.esq = dir;
    no.altura = 1 + Math.max(altura(no.esq), altura(no.dir));
    esq.altura = 1 + Math.max(altura(esq.esq), altura(esq.dir));
    return esq;
  }

  protected NoAVL rotacaoEsq(NoAVL no) {
    NoAVL dir = no.dir;
    NoAVL esq = dir.esq;
    dir.esq = no;
    no.dir = esq;
    no.altura = 1 + Math.max(altura(no.esq), altura(no.dir));
    dir.altura = 1 + Math.max(altura(dir.esq), altura(dir.dir));
    return dir;
  }

  protected NoAVL minimo(NoAVL no) {
    NoAVL atual = no;
    while (atual.esq != null) {
      atual = atual.esq;
    }
    return atual;
  }

  public void remover(T dado) {
    this.raiz = remover(this.raiz, dado);
  }

  protected NoAVL remover(NoAVL no, T dado) {
    if (no == null) {
      return no;
    }
    if (dado.compareTo(no.dado) < 0) {
      no.esq = remover(no.esq, dado);
    } else if (dado.compareTo(no.dado) > 0) {
      no.dir = remover(no.dir, dado);
    } else {
      if (no.esq == null || no.dir == null) {
        NoAVL temp = null;
        if (temp == no.esq) {
          temp = no.dir;
        } else {
          temp = no.esq;
        }
        if (temp == null) {
          no = null;
        } else {
          no = temp;
        }
      } else {
        NoAVL temp = minimo(no.dir);
        no.dado = temp.dado;
        no.dir = remover(no.dir, temp.dado);
      }
    }
    if (no == null) {
      return no;
    }
    no.altura = 1 + Math.max(altura(no.esq), altura(no.dir));
    int balance = balanceamento(no);
    return balancearArvore(no, balance);
  }

  public void remover2(T dado) {
    this.raiz = remover2(this.raiz, dado);
  }

  protected NoAVL remover2(NoAVL no, T dado) {
    if (no == null) {
      return no;
    }
    if (dado.compareTo(no.dado) < 0) {
      no.esq = remover2(no.esq, dado);
    } else if (dado.compareTo(no.dado) > 0) {
      no.dir = remover2(no.dir, dado);
    } else {
      if (no.esq == null || no.dir == null) {
        NoAVL temp = null;
        if (temp == no.esq) {
          temp = no.dir;
        } else {
          temp = no.esq;
        }
        if (temp == null) {
          no = null;
        } else {
          no = temp;
        }
      } else {
        NoAVL temp = minimo(no.esq);
        no.dado = temp.dado;
        no.dir = remover2(no.dir, temp.dado);
      }
    }
    if (no == null) {
      return no;
    }
    no.altura = 1 + Math.max(altura(no.esq), altura(no.dir));
    int balance = balanceamento(no);
    return balancearArvore(no, balance);
  }

  protected NoAVL balancearArvore(NoAVL no, int balance) {
    if (balance > 1 && balanceamento(no.esq) >= 0) {
      return rotacaoDir(no);
    }
    if (balance > 1 && balanceamento(no.esq) < 0) {
      no.esq = rotacaoEsq(no.esq);
      return rotacaoDir(no);
    }
    if (balance < -1 && balanceamento(no.dir) <= 0) {
      return rotacaoEsq(no);
    }
    if (balance < -1 && balanceamento(no.dir) > 0) {
      no.dir = rotacaoDir(no.dir);
      return rotacaoEsq(no);
    }
    return no;
  }

  public void imprimir() {
    System.out.println(this.raiz.imprime(0));
  }

  public void limpar() {
    this.raiz = null;
  }

  public int getAltura() {
    return altura(raiz);
  }
}
