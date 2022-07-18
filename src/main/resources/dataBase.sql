

DROP SCHEMA public CASCADE;
CREATE SCHEMA public AUTHORIZATION postgres;

CREATE TABLE public.client (
	id bigserial NOT NULL,
	address varchar(120) NULL,
	age int4 NULL,
	gender bpchar(1) NOT NULL,
	identification_card varchar(15) NOT NULL,
	"name" varchar(62) NOT NULL,
	telephone varchar(32) NULL,
	"password" varchar(25) NOT NULL,
	state bool NOT NULL,
	updated_at timestamp NULL,
	username varchar(32) NOT NULL,
	CONSTRAINT client_pkey PRIMARY KEY (id),
	CONSTRAINT uk_ah5c1ribskm746956okm9283n UNIQUE (username)
);


CREATE TABLE public.account (
	id bigserial NOT NULL,
	initial_balance float8 NOT NULL,
	num_account varchar(40) NOT NULL,
	state bool NOT NULL,
	type_account bpchar(1) NOT NULL,
	updated_at timestamp NULL,
	client_id int8 NOT NULL,
	CONSTRAINT account_pkey PRIMARY KEY (id),
	CONSTRAINT uk_14oi02p4g07lw28fce76j00ax UNIQUE (num_account)
);

CREATE TABLE public."transaction" (
	id bigserial NOT NULL,
	balance float8 NULL,
	created_at timestamp NOT NULL,
	description varchar(255) NOT NULL,
	state bool NOT NULL,
	type_transaction bpchar(1) NOT NULL,
	value float8 NULL,
	account_id int8 NOT NULL,
	CONSTRAINT transaction_pkey PRIMARY KEY (id)
);


ALTER TABLE public."transaction" ADD CONSTRAINT fk6g20fcr3bhr6bihgy24rq1r1b FOREIGN KEY (account_id) REFERENCES public.account(id);
ALTER TABLE public.account ADD CONSTRAINT fkkm8yb63h4ownvnlrbwnadntyn FOREIGN KEY (client_id) REFERENCES public.client(id);



-- INSERT INTO public.client  (address, age, gender, identification_card, "name", telephone, "password", state, updated_at, username) 
-- VALUES( 'Amazonas y NNUU', 32, 'F', '3333333333', 'Marianela Montalvo ', '097548965 ', '5678', true, now(), '');

-- INSERT INTO public.client  (address, age, gender, identification_card, "name", telephone, "password", state, updated_at, username) 
-- VALUES( '13 junio y Equinoccial ', 45, 'M', '55555555555', 'Juan Osorio ', '098874587', '1245', true, now(), '');
