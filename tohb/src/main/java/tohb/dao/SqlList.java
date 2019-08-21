package tohb.dao;

import java.util.ArrayList;

public class SqlList<E> extends ArrayList<E>{

    public ArrayList<E> list;//= new ArrayList<E>();


    public SqlList() {
        list = new ArrayList<E>();
    }

    public SqlList ad(E e) {
        list.add(e);
        return this;
    }

    public void addLast(E e) {
        list.add(e);
    }
}
