

use artelocal;

// --- Función MAP ---
// Por cada log cuya accion sea "vista", emite el par (id_producto, 1)
var mapFunction = function () {
  if (this.accion === "vista") {
    emit(this.id_producto, 1);
  }
};

// --- Función REDUCE ---
// Suma todos los valores (1s) agrupados por la misma llave (id_producto)
var reduceFunction = function (id_producto, valores) {
  return Array.sum(valores);
};

// --- Ejecución del MapReduce ---
// Los resultados se guardan en una nueva colección: productos_mas_visitados
db.log_navegacion.mapReduce(mapFunction, reduceFunction, {
  out: "productos_mas_visitados",
});

print("\n=== Resultado: productos más visitados (orden descendente) ===");
db.productos_mas_visitados
  .find()
  .sort({ value: -1 })
  .forEach((doc) => print(`Producto ${doc._id}: ${doc.value} visitas`));

