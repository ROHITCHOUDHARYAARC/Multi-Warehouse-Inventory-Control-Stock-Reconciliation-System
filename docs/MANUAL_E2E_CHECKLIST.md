# Manual End-to-End Checklist

Use a disposable database and seeded users. Record the actual result beside every item; do not infer a pass from code review.

1. Log in, refresh the page, log out, then confirm protected API calls reject a missing or malformed token.
2. With a warehouse-scoped user, attempt to read and mutate both an assigned and an unassigned warehouse through the UI and REST API.
3. Receive stock, verify inventory and a transaction record, then issue stock and verify the exact resulting available quantity.
4. Attempt a stock-out greater than availability and verify no inventory or transaction mutation occurs.
5. Create a transfer, approve, pick, dispatch, receive and complete it. Verify source and destination quantities once only.
6. Trigger a threshold condition. Verify an alert, recipient notification, unread count and warehouse-view indicator.
7. Open a notification belonging to another user and verify access is denied.
8. Open the warehouse view, select a zone, and compare available quantity and health with the inventory API. Disable WebGL and repeat using the 2D fallback.

## Current scope note

Stock-count/reconciliation and analytics flows must not be marked passed until their server endpoints and UI are implemented. They are not present in the current source inventory.
