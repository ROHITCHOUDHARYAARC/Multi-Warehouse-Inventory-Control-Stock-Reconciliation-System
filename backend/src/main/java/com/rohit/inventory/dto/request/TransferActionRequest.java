package com.rohit.inventory.dto.request; import jakarta.validation.constraints.Size; public record TransferActionRequest(@Size(max=500) String notes){}
