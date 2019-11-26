package com.example.redisdemo.selocal.stream.reduce;

import com.google.common.collect.Lists;
import org.apache.commons.collections.map.CompositeMap;

import java.util.List;
import java.util.stream.Collectors;

public class ReduceTest {
    public static void main(String[] args) {
        List<Foo> fooList = Lists.newArrayList(
                new Foo("A", "san", 1.0, 2),
                new Foo("A", "nas", 13.0, 1),
                new Foo("B", "san", 112.0, 3),
                new Foo("C", "san", 43.0, 5),
                new Foo("B", "nas", 77.0, 7)
        );
        List<Bar> bars = Lists.newArrayList();
        fooList.stream().collect(Collectors.groupingBy(Foo::getName, Collectors.toList()))
                .forEach((name, fooName) -> {
                    Bar bar = new Bar();
                    bar = fooName.stream().reduce(bar,
                            //此处可替换 (n, m) -> n.sum(m)
                            (n, m) -> {
                                if(n.getName() == null){
                                    n.setName(m.getName());
                                }
                                n.setCount(m.getCount());
                                n.setTotalTypeValue(n.getTotalTypeValue()+m.getTypeValue());
                                List<Baz> bazList = n.getBazList();
                                bazList.add(new Baz(m.getType(),m.getTypeValue()));
                                n.setBazList(bazList);
                                return n;
                            },
                            (n, t) -> n);
                    System.out.println(bar);
                    bars.add(bar);
                });
    }

}
