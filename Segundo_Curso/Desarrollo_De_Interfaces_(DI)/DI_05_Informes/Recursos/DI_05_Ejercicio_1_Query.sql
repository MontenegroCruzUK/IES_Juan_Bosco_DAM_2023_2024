SELECT
    c.id_cliente,
    c.nombre,
    c.direccion,
    c.ciudad,
    p.id_pedido,
    p.fecha_pedido,
    sum(d.precio*d.unidades) AS importe
FROM
    clientes c INNER JOIN pedidos p ON c.id_cliente = p.id_cliente
    LEFT OUTER JOIN detalle_pedidos d ON p.id_pedido = d.id_pedido
GROUP BY
    id_pedido
ORDER BY
    c.nombre