package SamsungAlgorithm22.Practice.StoreManagement;

import java.util.*;

class UserSolution {

    class Product implements Comparable<Product> {
        int bId;
        int pId;
        int price;
        int quantity;

        public Product(int bId, int pId, int price, int quantity) {
            this.bId = bId;
            this.pId = pId;
            this.price = price;
            this.quantity = quantity;
        }

        @Override
        public int compareTo(Product product) {
            //가격, bId, quantity 작은 순으로 (뒤로)
            if (price == product.price && bId == product.bId)   return quantity - product.quantity;
            if (price == product.price) return bId - product.bId;
            return price - product.price;
        }
    }

    class Sell {
        int sId;
        int pId;
        int price;
        int quantity;
        ArrayList<Product> productList;

        public Sell(int sId, int pId, int price, int quantity) {
            this.sId = sId;
            this.pId = pId;
            this.price = price;
            this.quantity = quantity;
            productList = new ArrayList<>();
        }
    }

    HashMap<Integer, TreeSet<Product>> pId2ProductList;
    HashMap<Integer, Product> productReceipt;
    HashMap<Integer, Sell> sellReceipt;
    HashMap<Integer, Integer> pId2Stock;

    public void init() {
        pId2ProductList = new HashMap<>();
        productReceipt = new HashMap<>();
        sellReceipt = new HashMap<>();
        pId2Stock = new HashMap<>();
    }

    public int buy(int bId, int mProduct, int mPrice, int mQuantity) {
        pId2Stock.put(mProduct, pId2Stock.get(mProduct) == null ? mQuantity : pId2Stock.get(mProduct) + mQuantity);
        productReceipt.put(bId, new Product(bId, mProduct, mPrice, mQuantity));

        if(!pId2ProductList.containsKey(mProduct)) {
            pId2ProductList.put(mProduct, new TreeSet<>());
        }
        pId2ProductList.get(mProduct).add(new Product(bId, mProduct, mPrice, mQuantity));

        return pId2Stock.get(mProduct);
    }

    public int cancel(int bId) {
        Product product = productReceipt.get(bId);
        if (product == null || !pId2ProductList.get(product.pId).contains(product)) {
            return -1;
        }

        pId2Stock.put(product.pId, pId2Stock.get(product.pId) - product.quantity);
        int stock = pId2Stock.get(product.pId);

        pId2ProductList.get(product.pId).remove(product);
        productReceipt.remove(bId);

        return stock;
    }

    public int sell(int sId, int mProduct, int mPrice, int mQuantity) {
        if (pId2Stock.get(mProduct) == null || pId2Stock.get(mProduct) < mQuantity) {
            return -1;
        }

        pId2Stock.put(mProduct, pId2Stock.get(mProduct) - mQuantity);

        int margin = 0;
        ArrayList<Product> deletedProductList = new ArrayList<>();
        Sell sell = new Sell(sId, mProduct, mPrice, mQuantity);

        for (Product product : pId2ProductList.get(mProduct)) {
            if (product.quantity <= mQuantity) {
                deletedProductList.add(product);
                sell.productList.add(product);
                margin += (mPrice - product.price) * product.quantity;
                mQuantity -= product.quantity;
            } else {
                sell.productList.add(new Product(product.bId, product.pId, product.price, mQuantity));
                pId2ProductList.get(mProduct).remove(product);
                pId2ProductList.get(mProduct).add(new Product(product.bId, product.pId, product.price, product.quantity - mQuantity));
                margin += (mPrice - product.price) * mQuantity;
                mQuantity -= mQuantity;
            }

            if (mQuantity <= 0) {
                break;
            }
        }

        sellReceipt.put(sId, sell);
        for (Product product : deletedProductList) {
            pId2ProductList.get(mProduct).remove(product);
        }
        return margin;
    }

    public int refund(int sId) {
        Sell sell = sellReceipt.get(sId);
        if (sell == null) {
            return -1;
        }

        pId2Stock.put(sell.pId, pId2Stock.get(sell.pId) + sell.quantity);

        for (int idx = 0; idx < sell.productList.size(); idx ++) {
            if (idx == sell.productList.size() - 1) {
                Product refundedProduct = sell.productList.get(idx);
                Product higherOne = pId2ProductList.get(refundedProduct.pId).higher(new Product(refundedProduct.bId - 1, 0, refundedProduct.price, 0));
                if (higherOne != null && refundedProduct.bId == higherOne.bId) {
                    pId2ProductList.get(refundedProduct.pId).add(new Product(refundedProduct.bId, refundedProduct.pId, refundedProduct.price, higherOne.quantity + refundedProduct.quantity));
                    pId2ProductList.get(refundedProduct.pId).remove(higherOne);
                    break;
                }
            }
            pId2ProductList.get(sell.pId).add(sell.productList.get(idx));
        }

        sellReceipt.remove(sId);
        return sell.quantity;
    }

}
