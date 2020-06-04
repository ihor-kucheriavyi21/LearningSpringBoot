Use sweater;
update usr set password = AES_DECRYPT(SHA2(password,512), RANDOM_BYTES(8));