package net.janrupf.ujr.api.math;

import java.util.Objects;

/**
 * Integer rectangle helper.
 */
public class IntRect {
    private int left;
    private int top;
    private int right;
    private int bottom;

    /**
     * Constructs a new empty rectangle.
     */
    public IntRect() {
        this.left = 0;
        this.top = 0;
        this.right = 0;
        this.bottom = 0;
    }

    /**
     * Constructs a new rectangle with the given values.
     *
     * @param left   the left-edge position rectangle
     * @param top    the top-edge position of the rectangle
     * @param right  the right-edge position of the rectangle
     * @param bottom the bottom-edge position of the rectangle
     */
    public IntRect(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    /**
     * Retrieves the left-edge position of the rectangle.
     *
     * @return the left-edge position of the rectangle
     */
    public int getLeft() {
        return left;
    }

    /**
     * Sets the left-edge position of the rectangle.
     *
     * @param left the left-edge position of the rectangle
     */
    public void setLeft(int left) {
        this.left = left;
    }

    /**
     * Retrieves the top-edge position of the rectangle.
     *
     * @return the top-edge position of the rectangle
     */
    public int getTop() {
        return top;
    }

    /**
     * Sets the top-edge position of the rectangle.
     *
     * @param top the top-edge position of the rectangle
     */
    public void setTop(int top) {
        this.top = top;
    }

    /**
     * Retrieves the right-edge position of the rectangle.
     *
     * @return the right-edge position of the rectangle
     */
    public int getRight() {
        return right;
    }

    /**
     * Sets the right-edge position of the rectangle.
     *
     * @param right the right-edge position of the rectangle
     */
    public void setRight(int right) {
        this.right = right;
    }

    /**
     * Retrieves the bottom-edge position of the rectangle.
     *
     * @return the bottom-edge position of the rectangle
     */
    public int getBottom() {
        return bottom;
    }

    /**
     * Sets the bottom-edge position of the rectangle.
     *
     * @param bottom the bottom-edge position of the rectangle
     */
    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    /**
     * Calculates the width of the rectangle.
     *
     * @return the width of the rectangle
     */
    public int width() {
        return right - left;
    }

    /**
     * Calculates the height of the rectangle.
     *
     * @return the height of the rectangle
     */
    public int height() {
        return bottom - top;
    }

    /**
     * Retrieves the starting X coordinate of the rectangle.
     *
     * @return the starting X coordinate of the rectangle
     */
    public int x() {
        return left;
    }

    /**
     * Retrieves the starting Y coordinate of the rectangle.
     *
     * @return the starting Y coordinate of the rectangle
     */
    public int y() {
        return top;
    }

    /**
     * Calculates the center X coordinate of the rectangle.
     *
     * @return the center X coordinate of the rectangle
     */
    public int centerX() {
        return Math.round((left + right) * 0.5f);
    }

    /**
     * Calculates the center Y coordinate of the rectangle.
     *
     * @return the center Y coordinate of the rectangle
     */
    public int centerY() {
        return Math.round((top + bottom) * 0.5f);
    }

    // TODO: origin

    /**
     * Sets the rectangle to an empty one.
     */
    public void setEmpty() {
        this.left = 0;
        this.top = 0;
        this.right = 0;
        this.bottom = 0;
    }

    // TODO: This is straight from Ultralight, but it doesn't make sense to me.
    //       isEmpty only checks whether all coordinates are 0, but not really
    //       whether the rectangle is empty.

    /**
     * Determines whether this rectangle is empty.
     *
     * @return true if this rectangle is empty, false otherwise
     */
    public boolean isEmpty() {
        return left == 0 && right == 0 && top == 0 && bottom == 0;
    }

    /**
     * Determines whether this rectangle is valid.
     *
     * @return true if this rectangle is valid, false otherwise
     */
    public boolean isValid() {
        return width() > 0 && height() > 0;
    }

    /**
     * Insets the rectangle by the given amount.
     *
     * @param dx the amount to inset the rectangle horizontally
     * @param dy the amount to inset the rectangle vertically
     */
    public void inset(int dx, int dy) {
        left += dx;
        top += dy;
        right -= dx;
        bottom -= dy;
    }

    /**
     * Outsets the rectangle by the given amount.
     *
     * @param dx the amount to outset the rectangle horizontally
     * @param dy the amount to outset the rectangle vertically
     */
    public void outset(int dx, int dy) {
        inset(-dx, -dy);
    }

    /**
     * Moves the rectangle by the given amount.
     *
     * @param dx the amount to move the rectangle horizontally
     * @param dy the amount to move the rectangle vertically
     */
    public void move(int dx, int dy) {
        left += dx;
        top += dy;
        right += dx;
        bottom += dy;
    }

    /**
     * Join this rectangle with another one.
     *
     * @param other the rectangle to join with
     */
    public void join(IntRect other) {
        if (isEmpty()) {
            this.left = other.left;
            this.top = other.top;
            this.right = other.right;
            this.bottom = other.bottom;
        } else {
            this.left = Math.min(this.left, other.left);
            this.top = Math.min(this.top, other.top);
            this.right = Math.max(this.right, other.right);
            this.bottom = Math.max(this.bottom, other.bottom);
        }
    }

    // TODO: Contains(Point)

    /**
     * Determines whether this rectangle contains the given rectangle.
     *
     * @param other the rectangle to check
     * @return true if this rectangle contains the given rectangle, false otherwise
     */
    public boolean contains(IntRect other) {
        return other.left >= left && other.right <= right && other.top >= top && other.bottom <= bottom;
    }

    /**
     * Determines whether this rectangle intersects with the given rectangle.
     * <p>
     * Intersections are only considered, if the width and height of the intersection are >= 1.
     *
     * @param other the rectangle to check
     * @return true if this rectangle intersects with the given rectangle, false otherwise
     */
    public boolean intersects(IntRect other) {
        return !(other.left > right - 1 || other.right < left || other.top > bottom - 1 || other.bottom < top);
    }

    /**
     * Calculates the intersection of this rectangle with the given rectangle.
     *
     * @param other the rectangle to intersect with
     * @return the intersection of this rectangle with the given rectangle
     */
    public IntRect intersection(IntRect other) {
        if (!intersects(other)) {
            return new IntRect();
        }

        return new IntRect(
                Math.max(left, other.left),
                Math.max(top, other.top),
                Math.min(right, other.right),
                Math.min(bottom, other.bottom)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntRect)) return false;
        IntRect intRect = (IntRect) o;
        return left == intRect.left && top == intRect.top && right == intRect.right && bottom == intRect.bottom;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, top, right, bottom);
    }
}