public class Main {
    public static void main(String[] args) {
        Cliente joao = new Cliente();
        joao.setNome("João");

        IConta cc = new ContaCorrente(joao);
        IConta poupanca = new ContaPoupanca(joao);

        cc.depositar(100);
        cc.transferir(100, poupanca);
        
        cc.imprimirExtrato();
        poupanca.imprimirExtrato();
    }
}
