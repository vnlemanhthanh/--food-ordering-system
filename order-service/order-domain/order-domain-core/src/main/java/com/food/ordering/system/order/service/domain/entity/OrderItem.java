package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.BaseEntity;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.OrderId;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;

public class OrderItem extends BaseEntity<OrderItemId> {
	private OrderId orderId;
	private final Product product;
	private final int quantity;
	private final Money price;
	private final Money subTotal;

	public static Builder builder() {
		return new Builder();
	}

	void initializeOrderItem(OrderId orderId, OrderItemId orderItemId) {
		this.orderId = orderId;
		super.setId(orderItemId);
	}

	private OrderItem(Builder builder) {
		super.setId(builder.orderItemId);
		product = builder.product;
		quantity = builder.quantity;
		price = builder.price;
		subTotal = builder.subTotal;
	}

	public OrderId getOrderId() {
		return orderId;
	}

	public Product getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}

	public Money getPrice() {
		return price;
	}

	public Money getSubTotal() {
		return subTotal;
	}



	/**
	 * {@code OrderItem} builder static inner class.
	 */
	public static final class Builder {
		private OrderItemId orderItemId;
		private Product product;
		private int quantity;
		private Money price;
		private Money subTotal;

		private Builder() {
		}

		/**
		 * Sets the {@code id} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code id} to set
		 * @return a reference to this Builder
		 */
		public Builder orderItemId(OrderItemId val) {
			orderItemId = val;
			return this;
		}

		/**
		 * Sets the {@code product} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code product} to set
		 * @return a reference to this Builder
		 */
		public Builder product(Product val) {
			product = val;
			return this;
		}

		/**
		 * Sets the {@code quantity} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code quantity} to set
		 * @return a reference to this Builder
		 */
		public Builder quantity(int val) {
			quantity = val;
			return this;
		}

		/**
		 * Sets the {@code price} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code price} to set
		 * @return a reference to this Builder
		 */
		public Builder price(Money val) {
			price = val;
			return this;
		}

		/**
		 * Sets the {@code subTotal} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code subTotal} to set
		 * @return a reference to this Builder
		 */
		public Builder subTotal(Money val) {
			subTotal = val;
			return this;
		}

		/**
		 * Returns a {@code OrderItem} built from the parameters previously set.
		 *
		 * @return a {@code OrderItem} built with parameters of this {@code OrderItem.Builder}
		 */
		public OrderItem build() {
			return new OrderItem(this);
		}
	}


}
