package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobject.*;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
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
			throw new OrderDomainException("Order is not in correct state for initialization!");
		}
	}

	private void validateTotalPrice() {
		if (price == null || !price.isGreaterThanZero()) {
			throw new OrderDomainException("Total price must be greater than zero!");
		}
	}

	private void validateItemsPrice() {
		Money orderItemsTotal = 	items.stream().map(orderItem -> {
			validateItemPrice(orderItem);
			return orderItem.getSubTotal();
		}).reduce(Money.ZERO, Money::add);

		if(!price.equals((orderItemsTotal))) {
			throw new OrderDomainException("Total price " + price.getAmout()
					+ "is not equal to Order items total: " + orderItemsTotal.getAmout() + "!");
		}
	}

	private void validateItemPrice(OrderItem orderItem) {
		if(!orderItem.isPriceValid()) {
			throw new OrderDomainException("Order item price: " + orderItem.getPrice().getAmout()
				+ "is not valid for product " + orderItem.getProduct().getId().getValue());
		}

	}

	public void pay() {
		if (orderStatus != OrderStatus.PENDING) {
			throw new OrderDomainException("Order is not in correct for pay operation!");
		}
		orderStatus = OrderStatus.PAID;
	}

	public void approve() {
		if (orderStatus != OrderStatus.PAID) {
			throw new OrderDomainException("Order is not in correct for approve operation!");
		}
		orderStatus = OrderStatus.APPROVED;
	}

	public void initCancel() {
		if (orderStatus != OrderStatus.PAID) {
			throw new OrderDomainException("Order is not in correct for initCancel operation!");
		}
		orderStatus = OrderStatus.CANCELLING;
	}

	public void cancel() {
		if (!(orderStatus == OrderStatus.CANCELLING || orderStatus == OrderStatus.PENDING)) {
			throw new OrderDomainException("Order is not in correct for cancel operation!");
		}
		orderStatus = OrderStatus.CANCELLED;
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
