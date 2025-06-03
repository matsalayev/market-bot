const tg = window.Telegram.WebApp;
tg.expand();

tg.BackButton.hide();

const selectedItems = {};

function updateCartSummary() {
  const totalItems = Object.values(selectedItems).reduce((sum, item) => sum + item.count, 0);
  const totalPrice = Object.values(selectedItems).reduce((sum, item) => sum + item.product.price * item.count, 0);

  if (totalItems > 0) {
    tg.MainButton.setText(`%%ORDERING%%: ${totalPrice.toLocaleString()} UZS`);
    tg.MainButton.show();
  } else {
    tg.MainButton.hide();
  }
}

function createProductCard(product) {
  const itemDiv = document.createElement("div");
  itemDiv.className = "item";

  const name = document.createElement("div");
  name.className = "item-name";
  name.textContent = product.name;

  const img = document.createElement("img");
  img.src = product.imageUrl || "img/img.png";
  img.className = "img";

  const price = document.createElement("div");
  price.className = "price";
  price.textContent = `${product.price.toLocaleString()} UZS`;

  const btnGroup = document.createElement("div");
  btnGroup.className = "btn-group";

  const minusBtn = document.createElement("button");
  minusBtn.className = "btn";
  minusBtn.textContent = "−";

  const countText = document.createElement("span");
  countText.textContent = "0";

  const plusBtn = document.createElement("button");
  plusBtn.className = "btn";
  plusBtn.textContent = "+";

  plusBtn.onclick = () => {
    if (!selectedItems[product.id]) {
      selectedItems[product.id] = { product, count: 1 };
    } else {
      selectedItems[product.id].count += 1;
    }
    countText.textContent = selectedItems[product.id].count;
    updateCartSummary();
  };

  minusBtn.onclick = () => {
    if (selectedItems[product.id]) {
      selectedItems[product.id].count -= 1;
      if (selectedItems[product.id].count <= 0) {
        delete selectedItems[product.id];
        countText.textContent = "0";
      } else {
        countText.textContent = selectedItems[product.id].count;
      }
      updateCartSummary();
    }
  };

  btnGroup.appendChild(minusBtn);
  btnGroup.appendChild(countText);
  btnGroup.appendChild(plusBtn);

  itemDiv.appendChild(name);
  itemDiv.appendChild(img);
  itemDiv.appendChild(price);
  itemDiv.appendChild(btnGroup);

  return itemDiv;
}

function loadProducts() {
  fetch("products")
    .then(res => res.json())
    .then(products => {
      const list = document.getElementById("product-list");
      list.innerHTML = "";

      const productArray = Array.isArray(products) ? products : [products];

      productArray.forEach(product => {
        const card = createProductCard(product);
        list.appendChild(card);
      });
    })
    .catch(err => {
      console.error("%%ERROR_LOADING_PRODUCTS%%:", err);
    });
}

Telegram.WebApp.onEvent("mainButtonClicked", () => {
  const selectedData = encodeURIComponent(JSON.stringify(selectedItems));
  window.location.href = `checkout.html?data=${selectedData}`;
});

loadProducts();
