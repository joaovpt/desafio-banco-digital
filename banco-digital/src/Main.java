import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Banco banco = new Banco("Banco Digital DIO");
    private static Cliente clienteLogado;

    public static void main(String[] args) {
        exibirMenuPrincipal();
    }

    private static void exibirMenuPrincipal() {
        boolean sair = false;
        while (!sair) {
            System.out.println("╔══════════════════════════════════╗");
            System.out.println("║          Banco Digital           ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  1. Criar conta                  ║");
            System.out.println("║  2. Acessar conta                ║");
            System.out.println("║  0. Sair                         ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> criarConta();
                case 2 -> acessarConta();
                case 0 -> sair = true;
                default -> {
                    System.out.println("Opção inválida.");
                    System.out.println("Aperte 1 para Criar Conta.");
                    System.out.println("Aperte 2 para Acessar Conta.");
                    System.out.println("\n");
                }
            }
        }
    }

    private static void criarConta() {
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        // Verifica se ja existe alguma conta nesse cpf
        if (banco.buscarClientePorCpf(cpf) != null) {
            System.out.println("Já existe uma conta com esse CPF.");
            return;
        }

        Cliente cliente = new Cliente(nome, cpf);
        Conta contaCorrente = new ContaCorrente(cliente);
        Conta contaPoupanca = new ContaPoupanca(cliente);
        cliente.setConta(contaCorrente);
        cliente.setConta(contaPoupanca);
        banco.adicionarCliente(cliente);

        System.out.println("Conta criada com sucesso.");
        System.out.println("Número da conta corrente: " + contaCorrente.getNumero());
        System.out.println("Número da conta poupança: " + contaPoupanca.getNumero());
        System.out.println();
    }

    private static void acessarConta() {
        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();

        Cliente cliente = banco.buscarClientePorCpf(cpf);
        if (cliente != null) {
            clienteLogado = cliente;
            exibirMenuConta();
        } else {
            System.out.println("Conta não encontrada.");
            System.out.println();
        }
    }

    private static void exibirMenuConta() {
        boolean sair = false;
        while (!sair) {
            System.out.println("╔══════════════════════════════════╗");
            System.out.println("║             Menu Conta           ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  1. Sacar                        ║");
            System.out.println("║  2. Depositar                    ║");
            System.out.println("║  3. Transferir                   ║");
            System.out.println("║  4. Imprimir Extrato             ║");
            System.out.println("║  0. Logout                       ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> sacar();
                case 2 -> depositar();
                case 3 -> transferir();
                case 4 -> imprimirExtrato();
                case 0 -> {
                    clienteLogado = null;
                    sair = true;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void sacar() {
        System.out.print("Digite o valor do saque: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        Conta conta = clienteLogado.getConta();
        double saldoAtual = conta.getSaldo();

        if (valor > 0 && valor <= saldoAtual) {
            conta.sacar(valor);
            System.out.println("Saque realizado com sucesso.");
            System.out.println();
        } else {
            System.out.println("Valor inválido ou saldo insuficiente. O saque não foi realizado.");
            System.out.println();
        }
    }

    private static void depositar() {
        System.out.print("Digite o valor do depósito: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        if (valor > 0) {
            Conta conta = clienteLogado.getConta();
            conta.depositar(valor);
            System.out.println("Depósito realizado com sucesso. Novo saldo: " + conta.getSaldo());
            System.out.println();
        } else {
            System.out.println("Valor inválido. O depósito não foi realizado.");
            System.out.println();
        }
    }

    private static void transferir() {
        System.out.print("Digite o valor da transferência: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        if (valor > 0) {
            System.out.print("Digite o CPF da conta de destino: ");
            String cpfDestino = scanner.nextLine();

            Cliente clienteDestino = banco.buscarClientePorCpf(cpfDestino);
            if (clienteDestino != null) {
                Conta contaOrigem = clienteLogado.getConta();
                Conta contaDestino = clienteDestino.getConta();
                contaOrigem.transferir(valor, contaDestino);
                System.out.println("Transferência realizada com sucesso.");
                System.out.println("Novo saldo da conta origem: " + contaOrigem.getSaldo());
                System.out.println();
            } else {
                System.out.println("Conta de destino não encontrada.");
                System.out.println();
            }
        } else {
            System.out.println("Valor inválido. A transferência não foi realizada.");
            System.out.println();
        }
    }

    private static void imprimirExtrato() {
        Conta conta = clienteLogado.getConta();
        conta.imprimirExtrato();
        System.out.println();
    }
}
