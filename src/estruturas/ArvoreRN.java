package estruturas;

public class ArvoreRN<T extends Comparable<T>> {
  class NoRN {
    T dado;
    NoRN esq;
    NoRN dir;
    boolean rubro;
    int altura;

    public NoRN(T dado) {
      this.dado = dado;
      this.esq = this.dir = null;
      this.rubro = true;
      this.altura = 1;
    }

    public String imprime() {
      final String ANSI_RESET = "\u001B[0m";
      String ansiColor = this.rubro ? "\u001B[31m" : "\u001B[30m";
      String str = ansiColor + "{ \"dado\": " + this.dado + ", " + ANSI_RESET;
      str += ansiColor + "\"esq\": " + (this.esq != null ? this.esq.imprime() : null) + ansiColor + ", "
          + ANSI_RESET;
      str += ansiColor + "\"dir\": " + (this.dir != null ? this.dir.imprime() : null) + ansiColor + " }"
          + ANSI_RESET;
      str += "(" + this.altura + ")";
      return str;
    }
  }

  protected NoRN raiz;

  public ArvoreRN() {
    this.raiz = null;
  }

  public void inserir(T dado) {
    this.raiz = inserir(this.raiz, dado);
    this.raiz.rubro = false;
  }

  protected NoRN inserir(NoRN no, T dado) {
    if (no == null) {
      return new NoRN(dado);
    }
    if (dado.compareTo(no.dado) < 0) {
      no.esq = inserir(no.esq, dado);
    } else if (dado.compareTo(no.dado) > 0) {
      no.dir = inserir(no.dir, dado);
    } else {
      return no;
    }
    if (ehRubro(no.dir) && !ehRubro(no.esq)) {
      no = rotacaoEsq(no);
    }
    if (ehRubro(no.esq) && ehRubro(no.esq.esq)) {
      no = rotacaoDir(no);
    }
    if (ehRubro(no.esq) && ehRubro(no.dir)) {
      trocaCor(no);
    }
    no.altura = 1 + Math.max(altura(no.esq), altura(no.dir));
    return no;
  }

  protected boolean ehRubro(NoRN no) {
    if (no == null) {
      return false;
    }
    return no.rubro;
  }

  protected NoRN rotacaoDir(NoRN no) {
    NoRN aux = no.esq;
    no.esq = aux.dir;
    aux.dir = no;
    aux.rubro = aux.dir.rubro;
    aux.dir.rubro = true;
    aux.altura = no.altura;
    no.altura = 1 + Math.max(altura(no.esq), altura(no.dir));
    return aux;
  }

  protected NoRN rotacaoEsq(NoRN no) {
    NoRN aux = no.dir;
    no.dir = aux.esq;
    aux.esq = no;
    aux.rubro = aux.esq.rubro;
    aux.esq.rubro = true;
    aux.altura = no.altura;
    no.altura = 1 + Math.max(altura(no.esq), altura(no.dir));
    return aux;
  }

  protected void trocaCor(NoRN no) {
    no.rubro = !no.rubro;
    no.esq.rubro = !no.esq.rubro;
    no.dir.rubro = !no.dir.rubro;
  }

  public void remover(T dado) {
    if (this.raiz == null) {
      return;
    }
    this.raiz = remover(this.raiz, dado);
    if (this.raiz != null) {
      this.raiz.rubro = false;
    }
  }

  protected NoRN remover(NoRN no, T dado) {
    if (dado.compareTo(no.dado) < 0) {
      if (!ehRubro(no.esq) && !ehRubro(no.esq.esq)) {
        no = moveRedEsq(no);
      }
      no.esq = remover(no.esq, dado);
    } else {
      if (ehRubro(no.esq)) {
        no = rotacaoDir(no);
      }
      if (dado.compareTo(no.dado) == 0 && (no.dir == null)) {
        return null;
      }
      if (!ehRubro(no.dir) && !ehRubro(no.dir.esq)) {
        no = moveRedDir(no);
      }
      if (dado.compareTo(no.dado) == 0) {
        NoRN aux = minimo(no.dir);
        no.dado = aux.dado;
        no.dir = removerMin(no.dir);
      } else {
        no.dir = remover(no.dir, dado);
      }
    }
    return balancear(no);
  }

  protected NoRN moveRedEsq(NoRN no) {
    trocaCor(no);
    if (ehRubro(no.dir.esq)) {
      no.dir = rotacaoDir(no.dir);
      no = rotacaoEsq(no);
      trocaCor(no);
    }
    return no;
  }

  protected NoRN moveRedDir(NoRN no) {
    trocaCor(no);
    if (ehRubro(no.esq.esq)) {
      no = rotacaoDir(no);
      trocaCor(no);
    }
    return no;
  }

  protected NoRN removerMin(NoRN no) {
    if (no.esq == null) {
      return null;
    }
    if (!ehRubro(no.esq) && !ehRubro(no.esq.esq)) {
      no = moveRedEsq(no);
    }
    no.esq = removerMin(no.esq);
    return balancear(no);
  }

  protected NoRN minimo(NoRN no) {
    if (no.esq == null) {
      return no;
    } else {
      return minimo(no.esq);
    }
  }

  protected NoRN balancear(NoRN no) {
    if (ehRubro(no.dir)) {
      no = rotacaoEsq(no);
    }
    if (ehRubro(no.esq) && ehRubro(no.esq.esq)) {
      no = rotacaoDir(no);
    }
    if (ehRubro(no.esq) && ehRubro(no.dir)) {
      trocaCor(no);
    }
    no.altura = 1 + Math.max(altura(no.esq), altura(no.dir));
    return no;
  }

  protected int altura(NoRN no) {
    if (no == null) {
      return 0;
    }
    return no.altura;
  }

  public void imprimir() {
    System.out.println(this.raiz.imprime());
  }

  public void limpar() {
    this.raiz = null;
  }

  public int getAltura() {
    return altura(this.raiz);
  }
}
