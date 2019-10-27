package com.mrebollob.chaoticplayground.presentation.checkout

class PriceRule(val price: Float, val discount: (items: Int) -> Float)