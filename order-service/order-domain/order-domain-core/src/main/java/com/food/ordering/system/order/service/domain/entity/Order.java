package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobject.*;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;

import java.util.List;
import java.util.UUID;

public class Order extends AggregateRoot<OrderId> {
	private final CustomerId customerId;
	private final RestaurantId restaurantId;
	private final StreetAddress deliveryAddress;
	private final Money price;
	private final List<OrderItem> items;

	private TrackingId trackingId;
	private OrderStatus orderStatus;
	private List<String> failureMessages;

	public void initializeOrder() {
		setId(new OrderId(UUID.randomUUID()));
		trackingId = new TrackingId(UUID.randomUUID());
		orderStatus = OrderStatus.PENDING;
		initializeOrderItems();
	}

	private void initializeOrderItems() {
		long itemId = 1;
		for (OrderItem orderItem : items) {
			orderItem.initializeOrderItem(super.getId(), new OrderItemId(itemId++));
		}
	}

	public void validateOrder() {
		validateInitialOrder();
		validateTotalPrice();
		validateItemsPrice();
	}

	private void validateInitialOrder() {
		if (orderStatus != null || getId() != null) {
			throw new OrderDomainException();
		}
	}

	private Order(Builder builder) {
		super.setId(builder.orderId);
		customerId = builder.customerId;
		restaurantId = builder.restaurantId;
		deliveryAddress = builder.deliveryAddress;
		price = builder.price;
		items = builder.items;
		trackingId = builder.trackingId;
		orderStatus = builder.orderStatus;
		failureMessages = builder.failureMessages;
	}

	public static Builder builder() {
		return new Builder();
	}

	public CustomerId getCustomerId() {
		return customerId;
	}

	public RestaurantId getRestaurantId() {
		return restaurantId;
	}

	public StreetAddress getDeliveryAddress() {
		return deliveryAddress;
	}

	public Money getPrice() {
		return price;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public TrackingId getTrackingId() {
		return trackingId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public List<String> getFailureMessages() {
		return failureMessages;
	}

	/**
	 * {@code Order} builder static inner class.
	 */
	public static final class Builder {
		private OrderId orderId;
		private CustomerId customerId;
		private RestaurantId restaurantId;
		private StreetAddress deliveryAddress;
		private Money price;
		private List<OrderItem> items;
		private TrackingId trackingId;
		private OrderStatus orderStatus;
		private List<String> failureMessages;

		private Builder() {
		}

		/**
		 * Sets the {@code id} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code id} to set
		 * @return a reference to this Builder
		 */
		public Builder orderId(OrderId val) {
			orderId = val;
			return this;
		}

		/**
		 * Sets the {@code customerId} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code customerId} to set
		 * @return a reference to this Builder
		 */
		public Builder customerId(CustomerId val) {
			customerId = val;
			return this;
		}

		/**
		 * Sets the {@code restaurantId} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code restaurantId} to set
		 * @return a reference to this Builder
		 */
		public Builder restaurantId(RestaurantId val) {
			restaurantId = val;
			return this;
		}

		/**
		 * Sets the {@code deliveryAddress} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code deliveryAddress} to set
		 * @return a reference to this Builder
		 */
		public Builder deliveryAddress(StreetAddress val) {
			deliveryAddress = val;
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
		 * Sets the {@code items} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code items} to set
		 * @return a reference to this Builder
		 */
		public Builder items(List<OrderItem> val) {
			items = val;
			return this;
		}

		/**
		 * Sets the {@code trackingId} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code trackingId} to set
		 * @return a reference to this Builder
		 */
		public Builder trackingId(TrackingId val) {
			trackingId = val;
			return this;
		}

		/**
		 * Sets the {@code orderStatus} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code orderStatus} to set
		 * @return a reference to this Builder
		 */
		public Builder orderStatus(OrderStatus val) {
			orderStatus = val;
			return this;
		}

		/**
		 * Sets the {@code failureMessages} and returns a reference to this Builder enabling method chaining.
		 *
		 * @param val the {@code failureMessages} to set
		 * @return a reference to this Builder
		 */
		public Builder failureMessages(List<String> val) {
			failureMessages = val;
			return this;
		}

		/**
		 * Returns a {@code Order} built from the parameters previously set.
		 *
		 * @return a {@code Order} built with parameters of this {@code Order.Builder}
		 */
		public Order build() {
			return new Order(this);
		}
	}
}
