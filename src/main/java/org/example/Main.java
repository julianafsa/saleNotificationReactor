package org.example;

import org.example.notifications.CartNotification;
import org.example.notifications.SaleNotification;
import org.example.service.SaleService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        SaleNotification saleNotification = new SaleNotification();
        SaleService saleService = new SaleService();
        CartNotification cartNotification = new CartNotification();

        saleNotification.getVendaFlux().delayElements(Duration.ofSeconds(5)).subscribe(sale -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(sale.getClientName() + " realizou uma compra");
        });

        cartNotification.getCartFlux().subscribe(produto -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("Novo produto inserido no carrinho: produto " + produto);
        });
//        cartNotification.getCartFlux().delaySequence(Duration.ofSeconds(1)).subscribe(produto -> {
//            System.out.println(Thread.currentThread().getName());
//            System.out.println("Novo produto inserido no carrinho " + produto);
//        });

        try(Scanner scanner = new Scanner(System.in)) {
            System.out.print("Digite seu nome: ");
            String clientName = scanner.next();
            Integer saveProduct = 0;
            List<Integer> productsId = new ArrayList<>();
            while(saveProduct != 4){
                System.out.println(Thread.currentThread().getName());
                System.out.println("Digite o produto que deseja adicionar ou 4 para finalizar");
                System.out.println(" 1 - bala \n 2 - refrigerante \n 3 - chocolate \n 4 - finalizar");
                Integer escolha = scanner.nextInt();
                if(escolha != 4){
                    productsId.add(escolha);
                    cartNotification.send(escolha);
                }
                saveProduct = escolha;
            }
            saleService.saveSale(clientName, productsId, saleNotification);
            System.out.println("Compra realizada");
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}