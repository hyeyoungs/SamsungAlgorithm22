package SamsungAlgorithm22.Practice.StoreManagement;

import java.util.*;

class UserSolution {

    static class Product {

        static class Purchase implements Comparable<Purchase> {
            int bId, price, initialQuantity, quantity;

            public Purchase(int bId, int price, int quantity) {
                this.bId = bId;
                this.price = price;
                this.initialQuantity = quantity;
                this.quantity = quantity;
            }

            @Override
            public int compareTo(Purchase purchase) {
                if (price != purchase.price) {
                    return price - purchase.price;
                } else {
                    return bId - purchase.bId;
                }
            }
        }

        int number, totalQuantity;

        TreeSet<Purchase> purchases = new TreeSet<>();

        public Product(int number) {
            this.number = number;
        }

        public int buy(int bId, int mPrice, int mQuantity) {
            purchases.add(new Purchase(bId, mPrice, mQuantity));
            return totalQuantity += mQuantity;
        }

        public int cancel(int bId) {
            Iterator<Purchase> it = purchases.iterator();

            while(it.hasNext()) {
                Purchase purchase = it.next();
                if (purchase.bId != bId || purchase.initialQuantity != purchase.quantity)
                    continue;

                totalQuantity -= purchase.quantity;
                it.remove();
                return totalQuantity;
            }

            return -1;
        }

    }

    static class SaleHistory {

        static class Sale {
            int bId, price, initialQuantity, quantity;

            public Sale(int bId, int price, int initialQuantity, int quantity) {
                this.bId = bId;
                this.price = price;
                this.initialQuantity = initialQuantity;
                this.quantity = quantity;
            }
        }

        int sId, productNumber, totalQuantity;
        ArrayList<Sale> sales = new ArrayList<>();

        public SaleHistory(int sId, int productNumber) {
            this.sId = sId;
            this.productNumber = productNumber;
        }

        public void add(int bId, int price, int initialQuantity, int quantity) {
            sales.add(new Sale(bId, price, initialQuantity, quantity));
            totalQuantity += quantity;
        }

    }

    static HashMap<Integer, Product> products;
    static HashMap<Integer, Integer> buyInfos;
    static HashMap<Integer, SaleHistory> saleInfos;

    public void init() {
        products = new HashMap<>();
        buyInfos = new HashMap<>();
        saleInfos = new HashMap<>();
    }

    public int buy(int bId, int mProduct, int mPrice, int mQuantity) {
        Product product = products.get(mProduct);

        if (product == null) {
            product = new Product(mProduct);
            products.put(mProduct, product);
        }

        buyInfos.put(bId, mProduct);
        return product.buy(bId, mPrice, mQuantity);
    }

    public int cancel(int bId) {
        Integer productNumber = buyInfos.get(bId);
        if (productNumber == null) {
            return -1;
        }

        Product product = products.get(productNumber);
        if (product == null) {
            return -1;
        }

        int quantity = product.cancel(bId);
        if (quantity != -1) {
            buyInfos.remove(bId);
        }
        return quantity;
    }

    public int sell(int sId, int mProduct, int mPrice, int mQuantity) {
        Product product = products.get(mProduct);

        if (product == null || product.totalQuantity < mQuantity)
            return -1;

        int revenue = 0;

        SaleHistory history = new SaleHistory(sId, mProduct);
        saleInfos.put(sId, history);

        Iterator<Product.Purchase> it = product.purchases.iterator();

        while (it.hasNext()) {
            Product.Purchase purchase = it.next();

            if (purchase.quantity < mQuantity) {
                revenue += (mPrice - purchase.price) * purchase.quantity;

                product.totalQuantity -= purchase.quantity;

                mQuantity -= purchase.quantity;

                buyInfos.remove(purchase.bId);
                history.add(purchase.bId, purchase.price, purchase.initialQuantity, purchase.quantity);
                it.remove();

            } else {
                revenue += (mPrice - purchase.price) * mQuantity;

                product.totalQuantity -= mQuantity;
                purchase.quantity -= mQuantity;

                history.add(purchase.bId, purchase.price, purchase.initialQuantity, mQuantity);

                mQuantity = 0;

                if (purchase.quantity == 0) {
                    buyInfos.remove(purchase.bId);
                    it.remove();
                }
            }

            if (product.purchases.isEmpty())
                products.remove(product.number);

            if (mQuantity == 0)
                break;
        }

        return revenue;
    }

    public int refund(int sId) {
        SaleHistory saleHistory = saleInfos.get(sId);

        if (saleHistory == null)
            return -1;

        Product product = products.get(saleHistory.productNumber);

        if (product == null) {
            product = new Product(saleHistory.productNumber);
            products.put(saleHistory.productNumber, product);
        }

        for (SaleHistory.Sale sales : saleHistory.sales) {
            if (!buyInfos.containsKey(sales.bId)) {
                buyInfos.put(sales.bId, product.number);

                Product.Purchase purchase = new Product.Purchase(sales.bId, sales.price, sales.initialQuantity);
                purchase.quantity = sales.quantity;

                product.purchases.add(purchase);

                product.totalQuantity += sales.quantity;
            } else {
                Iterator<Product.Purchase> it = product.purchases.iterator();

                while (it.hasNext()) {
                    Product.Purchase purchase = it.next();

                    if (purchase.bId == sales.bId) {
                        purchase.quantity += sales.quantity;
                        break;
                    }
                }

                product.totalQuantity += sales.quantity;
            }
        }

        saleInfos.remove(sId);

        return saleHistory.totalQuantity;
    }
}
