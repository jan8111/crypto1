package main

import (
	"crypto/hmac"
	"crypto/sha256"
	"fmt"
	"io"
)

func main() {
	c := getSha256Code("Message")
	fmt.Println("sha256: " + c)

	c2 := getHmacCode("Message")
	fmt.Println("HMAC sha256: " + c2)

	//sha256: 2f77668a9dfbf8d5848b9eeb4a7145ca94c6ed9236e4a773f6dcafa5132b2f91
	//	HMAC sha256: aa747c502a898200f9e4fa21bac68136f886a0e27aec70ba06daf2e2a5cb5597
}

func getHmacCode(s string) string {
	h := hmac.New(sha256.New, []byte("secret"))
	io.WriteString(h, s)
	return fmt.Sprintf("%x", h.Sum(nil))
}

func getSha256Code(s string) string {
	h := sha256.New()
	h.Write([]byte(s))
	return fmt.Sprintf("%x", h.Sum(nil))
}
