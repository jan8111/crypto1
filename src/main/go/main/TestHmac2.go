package main

import (
	"crypto/hmac"
	"crypto/sha256"
	"fmt"
	"os"
	"bufio"
	"io"
)

const secretHmac = "secret"

func main() {
	c2 := getHmacCode2( )
	fmt.Println("HMAC_2 sha256: " + c2)
}




func getHmacCode2( ) string {
	hash := hmac.New(sha256.New, []byte(secretHmac))

	readBlock("E:/Projects-test/vpshsdk1/src/main/resources/message.txt", 4, func (data []byte) {
		hash.Write(data)
	})


	return fmt.Sprintf("%x", hash.Sum(nil))
}





func readBlock(filePth string, bufSize int, hookfn func([]byte)) error {
	f, err := os.Open(filePth)
	if err != nil {
		return err
	}
	defer f.Close()

	buf := make([]byte, bufSize) //一次读取多少个字节
	bfRd := bufio.NewReader(f)
	for {
		n, err := bfRd.Read(buf)
		hookfn(buf[:n]) // n 是成功读取字节数

		if err != nil { //遇到任何错误立即返回，并忽略 EOF 错误信息
			if err == io.EOF {
				return nil
			}
			return err
		}
	}

	return nil
}

