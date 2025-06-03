package bot

import bot.Language._

object ResponseMessages {
  val USER_NOT_FOUND: Map[Language, String] = Map(
    En -> "User not found",
    Ru -> "Пользователь не найден",
    Uz -> "Foydalanuvchi topilmadi",
  )

  val MARKET: Map[Language, String] = Map(
    En -> "Market",
    Ru -> "Рынок",
    Uz -> "Bozor",
  )

  val PRODUCTS: Map[Language, String] = Map(
    En -> "Products",
    Ru -> "Товары",
    Uz -> "Mahsulotlar",
  )

  val CART: Map[Language, String] = Map(
    En -> "Cart",
    Ru -> "Корзина",
    Uz -> "Savatcha",
  )

  val CHECKOUT: Map[Language, String] = Map(
    En -> "Checkout",
    Ru -> "Оформить заказ",
    Uz -> "Buyurtma berish",
  )

  val DATE: Map[Language, String] = Map(
    En -> "Date",
    Ru -> "Дата",
    Uz -> "Sana",
  )

  val COUNT: Map[Language, String] = Map(
    En -> "Count",
    Ru -> "Количество",
    Uz -> "Soni",
  )

  val TOTAL: Map[Language, String] = Map(
    En -> "Total",
    Ru -> "Итого",
    Uz -> "Jami",
  )

  val PAY: Map[Language, String] = Map(
    En -> "Pay",
    Ru -> "Оплатить",
    Uz -> "To‘lash",
  )

  val PIECE: Map[Language, String] = Map(
    En -> "Piece",
    Ru -> "Штука",
    Uz -> "Dona",
  )

  val ORDER: Map[Language, String] = Map(
    En -> "Order",
    Ru -> "Заказ",
    Uz -> "Buyurtma",
  )

  val PRICE: Map[Language, String] = Map(
    En -> "Price",
    Ru -> "Цена",
    Uz -> "Narx",
  )

  val ORDERING: Map[Language, String] = Map(
    En -> "Ordering",
    Ru -> "Оформление заказа",
    Uz -> "Buyurtma berish",
  )

  val ERROR_LOADING_PRODUCTS: Map[Language, String] = Map(
    En -> "Error loading products",
    Ru -> "Ошибка загрузки товаров",
    Uz -> "Mahsulotlarni yuklashda xatolik",
  )

  val ORDER_SUCCESS: Map[Language, String] = Map(
    En -> "Order placed successfully!",
    Ru -> "Заказ успешно оформлен!",
    Uz -> "Buyurtma muvaffaqiyatli berildi!",
  )
}
