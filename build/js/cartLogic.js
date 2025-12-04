// public/js/cartLogic.js
// Lógica pura del carrito, expuesta en window.CartLogic para usarla en Jasmine/Karma

(function (global) {
  function addItem(cart, id, qty) {
    const amount = qty && qty > 0 ? qty : 1;
    const existing = cart.find((item) => item.id === id);

    if (existing) {
      // devolvemos nuevo arreglo (no mutar original)
      return cart.map((item) =>
        item.id === id ? { ...item, qty: item.qty + amount } : item
      );
    }

    return [...cart, { id, qty: amount }];
  }

  function updateQty(cart, id, qty) {
    if (qty <= 0) {
      // si la cantidad es 0 o menor, eliminamos el producto
      return cart.filter((item) => item.id !== id);
    }

    return cart.map((item) =>
      item.id === id ? { ...item, qty } : item
    );
  }

  function removeItem(cart, id) {
    return cart.filter((item) => item.id !== id);
  }

  function clearCart() {
    return [];
  }

  function getTotal(cart, catalog) {
    return cart.reduce((sum, item) => {
      const product = catalog.find((p) => p.id === item.id);
      if (!product) return sum;
      return sum + product.price * item.qty;
    }, 0);
  }

  // Exponer funciones en un objeto global para los tests
  global.CartLogic = {
    addItem,
    updateQty,
    removeItem,
    clearCart,
    getTotal,
  };
})(window);
