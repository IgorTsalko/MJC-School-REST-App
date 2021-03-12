PGDMP     #    !                y            gift_certificates_dev    13.1    13.1 5    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    41009    gift_certificates_dev    DATABASE     y   CREATE DATABASE gift_certificates_dev WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'English_United States.1252';
 %   DROP DATABASE gift_certificates_dev;
                postgres    false            �            1259    41012    gift_certificate    TABLE     K  CREATE TABLE public.gift_certificate (
    id integer NOT NULL,
    title character varying(100) NOT NULL,
    description text,
    price numeric(10,2) NOT NULL,
    duration integer NOT NULL,
    create_date timestamp with time zone DEFAULT now() NOT NULL,
    last_update_date timestamp with time zone DEFAULT now() NOT NULL
);
 $   DROP TABLE public.gift_certificate;
       public         heap    postgres    false            �            1259    41010    gift_certificate_id_seq    SEQUENCE     �   CREATE SEQUENCE public.gift_certificate_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.gift_certificate_id_seq;
       public          postgres    false    201            �           0    0    gift_certificate_id_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.gift_certificate_id_seq OWNED BY public.gift_certificate.id;
          public          postgres    false    200            �            1259    41035    gift_certificate_tag    TABLE     t   CREATE TABLE public.gift_certificate_tag (
    gift_certificate_id integer NOT NULL,
    tag_id integer NOT NULL
);
 (   DROP TABLE public.gift_certificate_tag;
       public         heap    postgres    false            �            1259    49243    order    TABLE     �   CREATE TABLE public."order" (
    order_id integer NOT NULL,
    user_id integer NOT NULL,
    gift_certificate_id integer NOT NULL,
    price numeric(10,2),
    create_date timestamp with time zone DEFAULT now() NOT NULL
);
    DROP TABLE public."order";
       public         heap    postgres    false            �            1259    49241    order_order_id_seq    SEQUENCE     �   CREATE SEQUENCE public.order_order_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.order_order_id_seq;
       public          postgres    false    208            �           0    0    order_order_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.order_order_id_seq OWNED BY public."order".order_id;
          public          postgres    false    207            �            1259    49488    role    TABLE     `   CREATE TABLE public.role (
    id integer NOT NULL,
    title character varying(50) NOT NULL
);
    DROP TABLE public.role;
       public         heap    postgres    false            �            1259    49486    role_id_seq    SEQUENCE     �   CREATE SEQUENCE public.role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.role_id_seq;
       public          postgres    false    210            �           0    0    role_id_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE public.role_id_seq OWNED BY public.role.id;
          public          postgres    false    209            �            1259    41027    tag    TABLE     _   CREATE TABLE public.tag (
    id integer NOT NULL,
    title character varying(50) NOT NULL
);
    DROP TABLE public.tag;
       public         heap    postgres    false            �            1259    41025 
   tag_id_seq    SEQUENCE     �   CREATE SEQUENCE public.tag_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.tag_id_seq;
       public          postgres    false    203            �           0    0 
   tag_id_seq    SEQUENCE OWNED BY     9   ALTER SEQUENCE public.tag_id_seq OWNED BY public.tag.id;
          public          postgres    false    202            �            1259    49234    user    TABLE     2  CREATE TABLE public."user" (
    id integer NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    email character varying(50),
    login character varying(50) NOT NULL,
    password character varying(60) NOT NULL,
    role integer DEFAULT 1 NOT NULL
);
    DROP TABLE public."user";
       public         heap    postgres    false            �            1259    49232    user_id_seq    SEQUENCE     �   CREATE SEQUENCE public.user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.user_id_seq;
       public          postgres    false    206            �           0    0    user_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.user_id_seq OWNED BY public."user".id;
          public          postgres    false    205            A           2604    49231    gift_certificate id    DEFAULT     z   ALTER TABLE ONLY public.gift_certificate ALTER COLUMN id SET DEFAULT nextval('public.gift_certificate_id_seq'::regclass);
 B   ALTER TABLE public.gift_certificate ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    200    201    201            E           2604    49246    order order_id    DEFAULT     r   ALTER TABLE ONLY public."order" ALTER COLUMN order_id SET DEFAULT nextval('public.order_order_id_seq'::regclass);
 ?   ALTER TABLE public."order" ALTER COLUMN order_id DROP DEFAULT;
       public          postgres    false    207    208    208            G           2604    49491    role id    DEFAULT     b   ALTER TABLE ONLY public.role ALTER COLUMN id SET DEFAULT nextval('public.role_id_seq'::regclass);
 6   ALTER TABLE public.role ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    209    210    210            B           2604    41030    tag id    DEFAULT     `   ALTER TABLE ONLY public.tag ALTER COLUMN id SET DEFAULT nextval('public.tag_id_seq'::regclass);
 5   ALTER TABLE public.tag ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    203    202    203            C           2604    49237    user id    DEFAULT     d   ALTER TABLE ONLY public."user" ALTER COLUMN id SET DEFAULT nextval('public.user_id_seq'::regclass);
 8   ALTER TABLE public."user" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    205    206    206            �          0    41012    gift_certificate 
   TABLE DATA           r   COPY public.gift_certificate (id, title, description, price, duration, create_date, last_update_date) FROM stdin;
    public          postgres    false    201   h<       �          0    41035    gift_certificate_tag 
   TABLE DATA           K   COPY public.gift_certificate_tag (gift_certificate_id, tag_id) FROM stdin;
    public          postgres    false    204   �>       �          0    49243    order 
   TABLE DATA           ]   COPY public."order" (order_id, user_id, gift_certificate_id, price, create_date) FROM stdin;
    public          postgres    false    208   T?       �          0    49488    role 
   TABLE DATA           )   COPY public.role (id, title) FROM stdin;
    public          postgres    false    210   y@       �          0    41027    tag 
   TABLE DATA           (   COPY public.tag (id, title) FROM stdin;
    public          postgres    false    203   �@       �          0    49234    user 
   TABLE DATA           Y   COPY public."user" (id, first_name, last_name, email, login, password, role) FROM stdin;
    public          postgres    false    206   �A       �           0    0    gift_certificate_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.gift_certificate_id_seq', 123, true);
          public          postgres    false    200            �           0    0    order_order_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.order_order_id_seq', 26, true);
          public          postgres    false    207            �           0    0    role_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.role_id_seq', 2, true);
          public          postgres    false    209            �           0    0 
   tag_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.tag_id_seq', 117, true);
          public          postgres    false    202            �           0    0    user_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.user_id_seq', 1010, true);
          public          postgres    false    205            I           2606    49340 *   gift_certificate gift_certificate_name_key 
   CONSTRAINT     f   ALTER TABLE ONLY public.gift_certificate
    ADD CONSTRAINT gift_certificate_name_key UNIQUE (title);
 T   ALTER TABLE ONLY public.gift_certificate DROP CONSTRAINT gift_certificate_name_key;
       public            postgres    false    201            K           2606    41022 &   gift_certificate gift_certificate_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.gift_certificate
    ADD CONSTRAINT gift_certificate_pkey PRIMARY KEY (id);
 P   ALTER TABLE ONLY public.gift_certificate DROP CONSTRAINT gift_certificate_pkey;
       public            postgres    false    201            Q           2606    41039 .   gift_certificate_tag gift_certificate_tag_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.gift_certificate_tag
    ADD CONSTRAINT gift_certificate_tag_pkey PRIMARY KEY (gift_certificate_id, tag_id);
 X   ALTER TABLE ONLY public.gift_certificate_tag DROP CONSTRAINT gift_certificate_tag_pkey;
       public            postgres    false    204    204            X           2606    49248    order order_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public."order"
    ADD CONSTRAINT order_pkey PRIMARY KEY (order_id);
 <   ALTER TABLE ONLY public."order" DROP CONSTRAINT order_pkey;
       public            postgres    false    208            Z           2606    49493    role role_pk 
   CONSTRAINT     J   ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pk PRIMARY KEY (id);
 6   ALTER TABLE ONLY public.role DROP CONSTRAINT role_pk;
       public            postgres    false    210            M           2606    41034    tag tag_name_key 
   CONSTRAINT     L   ALTER TABLE ONLY public.tag
    ADD CONSTRAINT tag_name_key UNIQUE (title);
 :   ALTER TABLE ONLY public.tag DROP CONSTRAINT tag_name_key;
       public            postgres    false    203            O           2606    41032    tag tag_pkey 
   CONSTRAINT     J   ALTER TABLE ONLY public.tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);
 6   ALTER TABLE ONLY public.tag DROP CONSTRAINT tag_pkey;
       public            postgres    false    203            V           2606    49239    user user_pk 
   CONSTRAINT     L   ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pk PRIMARY KEY (id);
 8   ALTER TABLE ONLY public."user" DROP CONSTRAINT user_pk;
       public            postgres    false    206            [           1259    49494    role_title_uindex    INDEX     J   CREATE UNIQUE INDEX role_title_uindex ON public.role USING btree (title);
 %   DROP INDEX public.role_title_uindex;
       public            postgres    false    210            R           1259    49523    user_email_uindex    INDEX     L   CREATE UNIQUE INDEX user_email_uindex ON public."user" USING btree (email);
 %   DROP INDEX public.user_email_uindex;
       public            postgres    false    206            S           1259    49262 "   user_first_name_second_name_uindex    INDEX     m   CREATE UNIQUE INDEX user_first_name_second_name_uindex ON public."user" USING btree (first_name, last_name);
 6   DROP INDEX public.user_first_name_second_name_uindex;
       public            postgres    false    206    206            T           1259    49465    user_login_uindex    INDEX     L   CREATE UNIQUE INDEX user_login_uindex ON public."user" USING btree (login);
 %   DROP INDEX public.user_login_uindex;
       public            postgres    false    206            \           2606    41040 B   gift_certificate_tag gift_certificate_tag_gift_certificate_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.gift_certificate_tag
    ADD CONSTRAINT gift_certificate_tag_gift_certificate_id_fkey FOREIGN KEY (gift_certificate_id) REFERENCES public.gift_certificate(id) ON DELETE CASCADE;
 l   ALTER TABLE ONLY public.gift_certificate_tag DROP CONSTRAINT gift_certificate_tag_gift_certificate_id_fkey;
       public          postgres    false    201    2891    204            ]           2606    41045 5   gift_certificate_tag gift_certificate_tag_tag_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.gift_certificate_tag
    ADD CONSTRAINT gift_certificate_tag_tag_id_fkey FOREIGN KEY (tag_id) REFERENCES public.tag(id) ON DELETE CASCADE;
 _   ALTER TABLE ONLY public.gift_certificate_tag DROP CONSTRAINT gift_certificate_tag_tag_id_fkey;
       public          postgres    false    2895    204    203            `           2606    49254    order order_certificate_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."order"
    ADD CONSTRAINT order_certificate_id_fkey FOREIGN KEY (gift_certificate_id) REFERENCES public.gift_certificate(id) ON DELETE CASCADE;
 K   ALTER TABLE ONLY public."order" DROP CONSTRAINT order_certificate_id_fkey;
       public          postgres    false    201    2891    208            _           2606    49249    order order_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."order"
    ADD CONSTRAINT order_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(id) ON DELETE CASCADE;
 D   ALTER TABLE ONLY public."order" DROP CONSTRAINT order_user_id_fkey;
       public          postgres    false    208    206    2902            ^           2606    49509    user user_role_id_fk    FK CONSTRAINT     q   ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_role_id_fk FOREIGN KEY (role) REFERENCES public.role(id);
 @   ALTER TABLE ONLY public."user" DROP CONSTRAINT user_role_id_fk;
       public          postgres    false    2906    210    206            �   o  x��TMo�@=����f�3���5ꡇF�\��H��lc.� ��w�NS;DDB�ه�}��n��|���C^<���e�K!%S�P"\K����A{᭵�����K4e��Ft��2[��G~��MǾ<�j8jOP����m_��l$�Ab��rAj�����~4ŎǦ�W�8���mξV�&��j���z�T� 8��Ǻ�H����)=��X9
���B@�����Hd˾(��鮮�c��O��N(�y4o�5*��jc��-w��f�_�5���.^���p{����E�@��PAZ�N�R/mP^�V�ŀb?�*�.�ݩ`G(/%� ��=1�.M;���i�f��1��qH�W��ٷԖ�)���?'<����#�PZ��f\,������>���G�w�b^�-��X�7:}@#���R'A�Nz�qԙM��}�m�nSw5�c�N�/�R�b!H/�*��+�;�@���9�5�f8�w���)5Gh��4�Ro	���[f�#��!4�}B�e6x0^-�tg�S��+��e��R_�2�X�����o���i�V�ߍI���+��?z�L��8b G�N�`�=s3�C��?���a����! �\�C����S��N,��-V|#      �   ]   x�5���0CϨ��C/�xs�4zA#h��=Z�y"��z�㯕��_�rZ-��Miorj�_�����P]�1m���Au�ֆ�������      �     x���Mj�0�s����[�Y����P�4�d��O���� ���� ?	S"�����A��ϔ�����b��l�Qc�	�DH��82O�5����,�#A:)��QS���&`�cA���1���01�d���qۚ��f����'-b�J�޵Vs�6��0�?1� Y�r[-�h.���z"Ϣ�V�.c,]Kx�œ�S���m����D��IQbq���w�p��"l�w<N����B�:~�x�_�k��ȫE��T_-�:�j�:�U˳��R,��      �   $   x�3�tt����	r��2�v����� d7R      �   �   x�=�QN�0�g�� 5�i�Kp��x��em�y��
\�&^�3�a5O��X�øK���� ��#Z�#��k�8�^����0c=/��%񁱅đ�$�4���Kt��	���-�����?������Z�����`����5E�`$Y���c�����O2��"cԾ�Zu[����_�c�2�+u	Ω�Vר�^���� [`h      �      x�d|ɖ���m���q�x��M=W���q��N��k��O���<�-G���*�Z�"v^Vf�(#d�%��1Y6�+��9������Lo��ݯ�{�_���6/W%�7�<��:�n�Ng���՜�����5I��C˲nJb�}⬭�� ��_3M�Yl���������[�Yϝ<���:�ǰ����r�Af>-�i޴�YR��8ŒA�~
	��:8)���jHTㇿ:�a��YB�.LSXҿ�ъ��n�䵡��u:�/]՜nji����{�.�bƽ��nhΧΛ=����Yb�� v�l�����v���q� �KXL�����|+��sMv��I1��Mag���ܭ��?����s�E�`��x���n,�)���6�	��;I���r�R��]����E� �p������Q�0��R��&�f��Er��ġ�"��Z԰��8KU��/+~�I�[����T�jum�Z���q�Y���J�ټ��z���1Zޭ�Ӏ�4"q����:��#H Z^�_��ok�~��_q�����=�W�çff��S�bЏW�%b���>L7�|��̯�I}���f�k����*# �� e2�IVPU����Y������qX}n��J��`1Ƒ�ո�oNE9���UC�w)P��v�b���3�h'AҺ�-a%�&�p �6������/��$*N8a����WЯ�^��ҙ�ŽQ�{g��0�� ^�x��YG,-��x�AiT$t"�cP:�~�R�\��?�nT\�-�~+�uiy�br7�P$�	�V_[�����#��Ɓ�j eE$�ŀ�������eX��t�~�1\#1t�k����ԯ| Ns��Y<���|�3�$���y�K	���)���x�$�
�}@f�G���-�c*a
���댜��Ki��2}��ѕ�y��|!5@q>�iW͘Bn�Yq��8���0GE\�R�*� .� %�qa��v:'���G���y[��h����h����sog�g~^�=�-)Y�[��T��k葥�����C1�%������W�|�a���e�H�s�f�H�E�C&�UM��\4d�h����O�~Nv�/S�7v�]
<'E�����"�`aY6��l"�0H�ӂ߉�]�Z�v���lt�s�ژӫ)=˽�5y����Rv��TK��I[���e�|���ZxY��׀�*aZՍ[�.3�QB-�ϊsڼ�(���ܻ��VQ����>���J}��d>��!֠�aJl3�!a��h�N��D�����ߩ��*�� :v���%'�`l�������T�o���FC�m@/�+��E���2���R�8�_�Y�J�9���>N�L�No���߯�h=�W֣��-��WV���x��8�_�X~b.\�X���xZyfSvY��A�;�.�V�)�h :'�ʬ��t�����g�p���r�f���z3$��(���l���׻�)]��U�ZE�ˠ�iy��(�QD���.��55Z�nF ���Y��L+�dV~f�,��c�a��3V�5f�6Z>�N�ݛ��گ����
�x(�t����;�ǿ���g��4xh���k�^��L:���y��+r���N>DG�� ����^���ə<�8Vo qɚԆ( ��_tA$�	��2\	}����ټ.��.��t_,�9;=��Os�,O7�0.�~}�1����T�btR���4@3��P)Z�$�f�@�3���C���أ�n�0���^�����z1�b�$��ó�Qן������^���}�7rr���O�	�]0���`D�2����m���yf71���wBc�)mE��u�z�e2�����iBY���������9�{9����� Mj��� 4�m��G#w�ʁ�y.G����G��2��M$Ϣ^Ǝ:jCc\����gu��dP�O�kzC'��q���N� ��)�| *G?V��R��ͮ�L�DO%؍�q�s/��W0>��7>]F��%��k�$��8�s�{p��qn Jr�0Q�E�=ޢ�5����"�'%�Ѵ%L~'}f}�<�K�;x��e�fF�^�s�1����L��ߗ;��t��U9�AQ8�&��ʱ��.B[MB���W~`�	>�@ӚJz��vNq�!�6Y��Yt��	?��\�fN�T��W?�m��|ʳ~�I��g{?V����CS���A�4BX?�	�繠_��ŗ�&vwh��_���/��8ι{��u���e���X�qPV%���8��7�B��8��	h�/<���u��7�r�6�7��L�\��������T�W�2cb��EˈX5=�����v�U՘
 *+]ZL�ʮB���mY��]iv���&�׌�*���}�4������~6��CZ$� ���� ;��@���(a��!��=H胤�"�����|�we���r6��V� -e�,�6|��[�nn�7���m�M�PT�ڌXg-b%�@x��U-��^���u1���|[���TL#�Ǥ���k��K���d�To3x&���/y����	3'�'?@5>�"���z��񎏮uJ0Li�ʹyW���s8�.>���/��3�d����=�����2>���x�b b cDZ���������,���oucG��wb=�[�܅e�D�ӥ˒bߑp՚v�zH�V�Az����p{G�����goL)���|�v�V>��8�c�"�<���/N���~����W�:��ϵ��7F#V����@3�o}zO�(y�*��Cl2�>�c�$�����~>ΥU҉":q�WM*3A�����|��}��w �u|�,���V�Of��%���;H8�8��$@94 )v�� *̰�'r��ۃr/�Ǖj�-g���l��-��a��M�a6�����C�C��8℠����7�����*DU��@���hf^rAt��/�ɞ/O��N2�Ԗ�=5�x�Oj�l��F0��3<��J׺$�j?Q�P�����;���D��P8
�9���}T�&��Ϫ��[�q�^g≆�W�ї+����c V�ࠥ��8R�E����%?���P��P��\�r��L���>:$Pᴭ�����v��]���b��`>�0�CP����(�⁢ΐ:�7��	z�	5�0j�v�ɆQ�����fy�B�fJ�/Kî�W'�)�<�����cJ�� ŢUz8h�hT�P��YUc��pN��,��%k���k�<�!��y��݌Y��6�����7u f�V�md�ĲqP{`�RV�|���bՀ�L�ZFտ����(1�����5��6t��Z3�n�n%ѷp0���p�]��i^«d)�d1rO�1ik��A����o�Y�(�-hG�D�iI&�?K���sv�_��i���b*��]���tn0CWq��c��	=�Q�!���E%��פ�Ee�<`#���$��eSa�HMtߺy�q�h����P_n�>'���)����O�rjz�� ϟ�ǽf�À���J�*Ц"D�kR����qJ˃С�2���<4���L�S�Aڼ�@E���yh�,w�L���b[���e�gL9,�x#��(�Sa���X��+@L~g������yz~>�6�Y����d07�W������M��4����3>Ya��KT�yMEx�8R��o��B�ր2>5��̔,t��!=���8�1��F��P�aV�����^o�x�n�<�L�|�X�P�J�q��EcWΘ�r��^�6:�?�D���+~�����g����xjSw�%�r���\�l��6ID=p�cBC�0����H��[���>��9� WjR���:�����������q�����7��ʮ�~��Q$����4#G�M$��H�̺�	� VU*�פ�Ea��Pmx(�y(�S��s�z��v��&��cS��uJ~v��v��Yp{<)qn��fH\`��f�����eQXb��^ �hЦ�~P�h���Y�A�I�x�v�%qP#�+��=R��h�����></sEMIes�ؗРg�(���;e`+h�    ԡ�;����A��Z!2����G�����D�{�s{s�θ�ކ��+.7�b����vS�Cx��e�e��[�	K�l��^�kR�K��"L�Hm�R�*����c+׃SEL�%���|��|W.[{���2���{Ȏs}ts��c���#b����j`Yh�S�:�hR�a�yJT�N��I�����d���<�r���1���r���Ny_V���a���4�!N���+[0�d��Ǣ��Ӫ��@V t�P�P0x��d}}|��Oz�yF�s����{��S��?��._>�-�|7�B�<1b,,e�H���n� E�Ѓ@t*j�	l�t>'�{��$�1\ց�	.7�˗�q]�[�䶚�4Ct��h������x��Bn�t�H��Fr'p0�&Q��X�ˣ�?���*�R���)���R�v�G���X'F�5�g� 9�4C���G>�*��'p�/N��YNM�����a�J8�en���3'#�ᯇ���.�|o�z�p̷7���-p1�,�����ՏEE�J�[�J�Q� ?���l���X�P�]��̂M�ks���,v�gzs����j�'�+��^�1�q"�	F���㯀p��I��#J�4+�IзBv�v�<��R�^�s������<N�s��a�z�����T�%:�D��CU�D�WϠ�T�"�����v�М�F��-�ʉY� O�@��(�B�jw��k}ߨ����$,6����[�EԐ�c��J^	ZP��}��|4@Mz ,ٷQ�j����t���0�I����S�p����s3<�5)�yiZ��܁B�y��E�=,HT��>Q����&8p���Ad��&�&��kby٘i���d^�S�h�%����Z]B3��h���ȩ��mh^��_�F�Jb�`շ��)�/@`(6�Q?�,K��$�
{{������~څ1K�\n,0�t�s��,�L%x�N���{c7��p1#�M]#�U�H�զ�6Q���Q�&� ��I0���}fu��N�zy��Z�SO���>V�Fg��������γ�R�4"��=���ˢ}D���uj�
4��c���P_����V��ʝ��;gIrSn�=��>o͍ʡ�%z�FW�e��U-����՗o�����6���sY=+�35��Y9�
E�ekwn���}�����ύŞ�?��x�X�AF��qP��cQ���)D��q���0!�I88f�⠪h�0���}�hU{x�I<;`�l*X�r����S����b90���G�Y�#�@��j;�7�I,�O얅�%?>|�Ћ��]��ϻT�:q�B`m����LҶ�o,��1���eP�/�,m��G��}-�]Y5_Y�G�4��I5�3� �f6a6��qɒ�9x��k�����h���	(��C��<�Z�Dh#�!� =V���q��ES�|Os;�ǻ�$�X�R?���oCr<=�d��k�([���m��F�S �Zl=\��9�5h����pV
"������.��=5�z��&:)�cA���͓4}<o��{�C}�G�9;��S70"(�,QNC�$U��8Râ����%6*)��D�K�#r��mh"��l`dlwl��^44GQ:�N�v��6�}T��X��L�"��5֓���a���|�M�Y��S�z��񼦭[3����>�7>`&�����=��u����X?dy�+0�>KK�rg���!���/\TUF�dPѓ|p�H�何?��t�h��	�Sk��{��+�>��s)��.�pex�c\)�|O�F9�!P(���n������!��##0Ǣ��s_���TӼmafwf�mi������T�{��Tu}o�[G\�E�3T�J������#��(`MO"����4VJ����=y�AE���]'�S��%�O�d8=�$�C����#�DO��>(����fQ`G8&P.�� 划�4O���O��Y#��3�Uͺ��Ө(QI��M/[qvx��&o���/o!N�@\�E r4}��!Q:�&�.� Q�,Ea�f=e����$'p>�/���V�r�ؙ!S�{�#�U/:uG�h,���(�����1TD�R2T��k�s+be���hn�U�|QPRWzG�0LZ�����ِ�Y~���#���h.3TA�R�iC�z�E"�qF�6AU�q����'�#*���ѓ���JAώ���V>wK�'�$��RG�$-���W�엖������S���Œ"�0�f��k�¿h+e�ғ��4X�{��'{��3uE�qS{�O.s�n}�X�����Վw)��)�P��r��GсҞ=�����}��8kz����$�9�Zt����]#����#K�*X�G��g~�!�������O	<M��`e�q��l�,G�!��!G�'����s��r�٫�d<'�lAXѝ�tZ/��*�8$��^�+�^�kc���B��t<�������j�W�m�z���NO]63���J�,���h��H7#eO��Dځ�[�7ٽퟏ�;�Ȣ�h�ʆ���	M�Z|1ej��V+�:��$!�wO_�{���^��)Ȑv�w�!(b�G���t���؝�����~�2�A���@�(<�/����FL�#z~��'�j�~ؙ��l�7)	N���׋�';f��f��\3�����8�X�p1�,�Y� �b�����Ċ�D��pP��r�f�ky-ܷ�l#+SɌ�����k�,�@wm��eY��Da�+�8�&X=.��( }�J�탔����"�Qշ�B0���f0t��]�M�s79�~7˳d�=�S�O�E�8 3���T�� %bDI<���w%ȭ� I0^�'�`;$ۈ�_7�m�dѓ��je���l��_]g��ou��*���p%� "����5��7��|��'�[��8?Y�½L���������@�&=�(���*2*�pdX��h�~�}p�t[�z�m}��˖�h�1Da��ZG_���0ʵ8p���'�	=���.xm��1�hw�ri�W�#)�ئ���F5s����/�"���p	P\]�:@8(�c�94�j��	�%��IEx�̬?�co�43��o�aH�����W�E6y,��sX���i�ƶ)��Pz�13
�hDh�.~:�j%�a@OBo��Zf��rN�I�h#�lЇ����V��i#�npm�9z
I�e@q�EIT��#-���J@�Д�]��'��C�b�0��eW|���}oG�>�ȫTl��<�C2��j;Q�� 8@%�89{&Q�@��Xt�Ʃ�_�"�E 0�'���fS��Pɲ�ݕi��xp�ru�o;)���()[�����Xמp��+!*�ֈ m�XQUx��H/&B�=��$��y��u�4�[Ŵ���浒�l�u���M�7�n�a� ���e�;LS�i�O:]PM�PwUU��'���J9���^>����aGh%OD��[��v��YTz�O�7�k��R �р<����>pQ�(� ���UM!QF��R��6)�Ԭ�g^�p�Q@�`�+�ٹ7մ�카�g#�a5�-aT�����`O�%:B���A�c���0f�g�*�X\���Ε���kgh;Z��Z�ˋ�
�MҼR�	7���a�'I�Ą�Xr�ɐ]�Xt�ȭo &>�0��T�����b�m����s"6���{�r���u9���t�&���ퟞZ�:0��*��Xv(���נ������O�΃iz�N��ݮ�G]zyn��w�u�6)E�R�̖ϩ�2��l��9���4��*Q!�aE��Ǒ����9�F�3�I,�CCȎ�sxT�����{i��߇x]�f�\>�-@i7I������	7��M␥@T/L�]/������TڮM6�ڨ�!�K|[O�?�]���v�.o�7���.��u}��8��UjcZ�9*Nx��E�+�?}�I ��We�t�y?���.�� �2� �½�孒2�5�^%wS)��d�FD}����X    �l�]�%����!vq��L
�J�
�����Q3a�K�t?���Ŀ�k�_zRt��
��$��R�g�F=���5J��pƑ�(�Ր�{�n���aEOBA%1�:ӯ�ه�3�Ff�Ugގ���c���~�(�2�v�j;*�6�"��[� ��(�0��#F �x�5��$p�-����ޗ�'�9�&%EjR�6�<���n~��p�
riW�|]\O_'E)��#�������h��"�I`c�U׸Z���~>hB��Qk?�GI�&7��=�S�V^�%ם�u� 1��ޟ�{"���"��Q�E�?���A� u`�]┈�}��pm�,߾nϮ!��`ޑK���R��e8ӕX��m���B���N*}ϊQ��8W���Mj��D2�(��6�?̤��8�/�:/��Yu�o�^K:��|�lo��ƪ�a��
�c�Ps��Ҁ0�oKj�}����p�j�'f	6v�^�T-Ϻ�Wً��j}�6Q���f S���t�_"�mp�R)�X58V@��aMX��`�_�� �E̤�@z�����R�Ȗ�K�S��*I�H�C�f�~g$� �k�ڕ��'Jш���s�:��+A��`������q��Xf�	�����u��1;�I���ӟs�=�yݡ���%�o��΢��njC�O%&9(v�k2�"���U��I�3�]/w�}�������ؤd�n�@�y�Z��o	�FV� ��8+�R^�@�&�	7�����=p@:�t1�<pd$)ظ�ڮg�lG2��^4�X=��.:�&�7�.��0�vK�=�_/�� =�N|�U����$�\��;eQ�0�@�C�mޗ�}\�(��ݞ[��Q�V|EvNo�U����b[M����1�D˅�W���0
/_&���b3��DyዮܝZ폭*�n5��(�{��m�x��ND���Z��QN�uV�}�3@b���iMT֏��i-�*|��{�L�@[�&Jb~]פ�o�Ŧ{@YM�RU���������u�onv<h�1�S"�#ChA@���k0��������F�L���z8 ;������]k0o���/�xzm����ؖy���U�[�)i��`&O1�����XX�]�`�W��������L
�~'.ո]�2�uϰWf4�FK��X���v�IǏg)է����T��V�,�� =4U�����X=�?G�9���Ṳ�%���!��|�h�qP��p���zf�����A�V-��Kj���m�qV�0J���,��x`���F���v� Č6w��m&Շ�{׹�w/�r/1yi��r���FW��]��ٙ�
&�o��w4�_86t�NF  4���S�Ȫ$�	���Wf�#�u�iN��f��Q^��+tK�����6?�����6��W����N����֊oyB��w�̌���ӝ·b)��Z�sрd���"~}���#��͐>��J�}��xP 0q"Lmee�����d��J>ʹqV2u�eQ��/���݋�	��ЊL�2�����x߼���+$�|�T��2"�/�e-Dѿ�-2�ַ��Y�03��<�Ĭ��xWt�5�BuKKp��������9�t ������s�4�hd�����ҬÍ�xd�E�b�U�Q0��A�L�@p����]G��;D�̈́���Ge&��,����l��d~�܆c��bi���K��`�?4�N�c&E �Tk[��[��� �$�>���&��(�ypG������gi�;�N$�N`LN $D�3C�����H~��5�x�>kO�g�7TU�}�u�� _��x�v��6��}&��������Db���~J|��A�yx�d�]���U�V���g���H���,�d|����z�6�k�P�� l��=�w��&+�x`����(�Cf��{��,dז�=��qV��X׷�ϳ�h+�g�FW���ы�r�~��2�Q��
��`��V�|ຨv3�&�ft�2A�(w�u�d~j�"�j�Y?>����扤�n�srup:��S�/dĨGL���h9��I3�f=��L������h���m샼�P��HZ\���Q3Ղ�`�%wh���B��GwAxi����D��L�V�|�+��cU�� �8]�i�]������4#On���T�Se_��b�d�;Ւ�?�J�ߙB�J�i�oy�c1����y��yd�0�P"�D��(������]!X�E�>���EC9W{��� �����Dj~3�I�^ %�X���82��b�A�I�w��W���JQ��Z��|�8+V;��)�'篓;w��Jw�/�����{���� �w�ҁ)ݷO� ì�1)3��ޟ���N��tF@���ߠ\�R���\�����xZ{�}��q^�X�;D���\��ѯ�T���o��H�$��u�,��Z��x��,K���.��~�J��eZ3���1q���nT�wV���x���2b���Կu��-�AZWȽ&����2�gAX�_�>��n�nKN���v�z?3�$���=��f�d�=] ��*�[+�p���
��o��|��LJ�Q��:�H��֭��,=[z��]�9�T�]��h�����]܍O���F)��ߣ}X�D�XL���g�y�2�JP����T�K��U6,�H4�]�6�?d�\����]�ڳ�g����{���<��5&� G����Ck�E	R�����J�n�������������-폞ޗɚ����9���o'��0�/�� ��lR�̌����/r��(�Q����?0�^Ы!�m��3����U�u3|4*�	K��^�.WG�u�����%��ʌMT��E�����S�Ƈx㬞=v�̤09���{�=��,؍u��p[����G�P��pni�b��g��5Rw�E��B`� :��Ǒ�,,�O �!?9�q�?J�2WI�V��I�_��xtܹ	��4�P*�(�҇Ҁ��S�ݘ�X����]h{�)"H�;_$Y��e|���?��$��#N�MT]o��r��T�ss������g��n����b�]�T;�ˣXUi����u�8#`�5�\I;ܥv
v���޼�{~_;9�v$eg�]�?�A,�JRƂ��b zd��@(��-$�u��_����{8z�W�(��:h�|�d��y�b��d���\�wփ�����6���v]?%"�/�vI;�.����Y�=a�g�l��
;���׈�̺�ĝޘ����X�$~eͥ.8r,g3F�Mqe�"���
1��3�I	��,��c�۬XT���p��f'�@C�a/Oay�+#S����1��!�?�wU��EL��:����ގЋ��>�ћ+#u<�,7r����'����OAg�� Á�g�ڒ��M�y�/K?�;�׫�т�@��� �ԸF�"�6��X��c#9��fw�{}�˲�I`�w����?+[��(d��M ���*�ˣs��Q
w��zQ㑣Qd�6�q�T@�㢑YG�Y%�,���`����@���?F�;8|�W{ut�������񶪸��5\5��~c���.0C� ��<
������y	��أ��N�����r^<���r����6�p3/�"���S�{A�hv�Nǲ������Zs�q�@�VZ��L����}�*���1;��ع��3����F����V:�|ԗ0�����k%�*�v��R9!��
 ��l�5X�l%��;6u��<���2O�}-?:�+�s�����7av�I�u҆e^�ʹ|�{p�.�'n1�ɿ�TC4��Z�ծn��;$�	�y�M�2;�b߶Ϋ�����C���&<J�Y�(��pJ=]y��>%'��Q���}yߗg�Eޔ����GZrQ&����$��5%9��sz|07N�U�a}
��.dH��. y�'-��S?�G��ۚ��֌<������k���Ϻ�n\���觟�PE� ;	vD*;�T�y�*���!�=\�4���z�W�]_�^7����ꥏ����bs��V�5X8�    �cg��v	D���)���}%E��2�e,{�>���|f��l��a�]���M�=-��x�1n���QaE$pYwQ�t�Y
��4�w�I���ʽB��d�3��5Iu�
���4^6�]	��zW��x
��ߔ�hw��NF����v���9
v���q�ު ���misyC���ʩ����#%ίo�=��unJ>MǄ�ӄ	�6�ON��fD�H36X�_M�^� (V��45����L��� ��sQ���P��G���{�z5o%�k���-X�����U�_�D��8��߻C��A`��d��毭h~m��r=�yfld�pc*}'8�L�Ws��Z�h�Z��(j�,qG��m�=���6\4I:�U�{�ȏ&a@��C���z#~��WC�Kr�^Y,��#&�D��_�_�i3�a��@|��6��k�Ѣ���;;�b/d'u���C��ԋ���Yt֚�f�;�"Y��>9�T������������k|����}6F��o~{�]J̈�I �X1ט��޶(�O9x����<�\լ��{6| k��N�A3N)Kwd|��J�k�ɟ�P��V��c ;�H���|���^W��u!I�[�Ia	��mrv�ur�Ю�������B|����	�Ȧ��U���ڀ�D���[��kr���r�@<o����Y
iH϶T/�!�KY����|�|�����U_��a�8_���{�I%0.������x���5��3�І�q��!�)9���6}�s}����0'.A�_���(Kl����A�%�W�e9e�E6}��Jr�>ʭ
�S�Tі�Du7`pdz[�9y���N	�ჴG� ��_v�&[��G� c��tR���b�j��Yig!ߝAb�F���T��Q�����a���Uo�?Ӣ�ӌW�W ŉ��Ƒ-�}��}S��N� '4䙋����z�m����j�+�.��f�o�ayi�t��j�mUo�C��ݭ(��?�������թ�\v�I~��jsM���M���]��.��ع�o����R�|������P�d?��k"��֯��oow
��-';���1N���<��������̩����fb.�"��k9]UrcH�Ә|��c�´-����GJ��('M�#�*i'B�a��tߌyp�t��8O�:�=-�щ:K/��?B��Gܨ8:��$D��Xl�@q�����{:��z����e�mї��"��Ƹ��x+t������~Vg�$�қ�m��zෝ{0������:?�s��Iv��<�=ի��GX�.J�����jo���;j%�����_�]�F}�GD�����67oQY�Q�{��w����P�-�h�or����.�R�첼hK���e�Z���3����`.�72�1 qϗSa)+��;|/\ׁ� �j�J�T�aj9��+C�[���0?�,���E���r�k�<���J���>�)�%���Χ�`��2 �?��g�۴jD�ô:�>���D^�����{�w�a�A��p6ddK�!��qY�P֪%ix�5��R$�PP`~ ~�"�n�!�v�&7_��g�!�����\B������x
���p�UeW���c�j��O�O���ܢ}�y�#�i�aK!B��I����t����W�!�&Q�ԭM!K1���Y�с�I�����J4I؈f{V=���j��WS�TKdƞ%|���%��A����[B�A�;����d�]�>���~tz2$m�c &R�P���-xK����2�]���D��7I�%��+�����"�
��X�&a@J��G�\͑H���~�t�{1��?�u[G���git��txy�wZ�R�YL\A�����X�.��Zz��c7i�މ���z��T�P���}w�ð�%�8o�5Êʱ&kKJ�ʈ�E�XC�>����`�/�����S��P0:�&y��s�%v�%[��ι���bq���~�l��H-r{+1ʙ����c�OZ���(BU�*�l�_0�^��:ܧ�-�&� b�i�]��ɗ,n��GP���C���g���*T�U@'��V��p��%J�1��*��&dL6'L��JDS����I6p��yFyf�T�s\U[�-���˟o˭�ʱ�D��rO�!(����f	ms5 ��[�*��'.�8p�X���h�!�c�M�A�bTg����.�u��)�f��ܡM)'��QH#"��ӎ9X��W��c6�2�u��'-�
�|�D��&�%�I@�֫((�5u+Z�W���	�~�	�����qQ�aR�kHѰ�VB��I�Bsj�1f��kp`���E�/��M�?�1,�:����.V}����+d̫ьo�m'�aE������ɌUU�qN�W��
$���������g�D/��w�\y�{kn~��6޹�pX݀k2��^o.��Ȳ��a,6C<Q���Ch8��އ?gOgCu��+n�L�Uj��B��+�`����ԏT�P�A�*�_��1�^|�%���=A����E �*<r΢r=��7g�onR�(�읣@�J"=�@�����چ힟��=Z���s�<3Ni�A3r�CY��+Ee=��Ћ�|�࿺5 ppO@�M�����=U�9���W�6�y�q'�=mË��wX��~�jo��0&�'N0�l��4�O@����\� ~/�w�р�.7��Y*E���>L���s����x�z�3��婹����N[��TP��	�G��M ^8�����yq (Q���S[J�����,˳���]�r��7�sp�x���n�ě�|��N�x�]	 ����
�[���&�/L(��G�[�q���4�����N_��a�Dl� /KR���z�T�b�O�GQze3/wyz���a"r�
�qD��d�8r�b�,��c�5p��@�=�̧�������ER0);lf _VF���O�fZ�3���n�ߝ�i�2��%:0�\��ƫ��������OO*n�ҭ�m����P��Mԑg���I�̭��֌�1����*�*}s|�ݜ¯̾���xE�h���E�g����B���p�7�+��1���=�<�F{������s��Ƭ�����>ߗ��ibU��E*�,�Ǳ@c�>p�"���'>�*9�&=avlUW�R�r��on6|:�G�n/��+���c�w���R����qbf�݊R�ߊ9�0K~,.���i-g��~'}���݁ۻ�A�zv���KC�t��:J���V�3�^�|�W�qR�獸� �v}.E��M��>��B+�Ą�گ�b���3ԅ��C7���z���5�|��s|�^\U��_�����ïoCq�K|�d"�&�-FqƸ3��!�j�IQ��Q�X��P�����l�ɖ���5����֏��^��cpO�5����W�s����k���\c% �fLE�(���b�s6)����Tǒ���'O���]��b���
�;X�QJYq�W���ҭ��/h��^��{����V��K�߶W�&R�6'孽�[�L$g����H�*F�w-	��%���h���Ab�+P^���K�3\�0�ӘrU�u�\�-�<6��]�k���?գn�l�9���xL��qs7�wI���k!
��qq-+�
�\��OT�?6��1���'swd�e��s��W"X=ȩMW�ܟK�-�����E^b���|�1�`{����dh����U8���Sz�:qt����˻wm�¹��!��]j��&Ӳ<T�͍���"��[� o�R���G�E���/�������/(K���&����)�cX~D�=����r*�4/�y'$��G�H�x�P�Bѵ�}�g&�^h��61�S���snq�
������^�&9sZ���#�V�q���:�-g�:��'1�Ԉ%+)z�e��%yf�t����Vx�/�>���C'!��z�t�h糫�Bzt�%�n�w��u;��޵���Ҵ�KVR�A��ق
1���3���E	����$�p3��?����/;� �-�doD���RZ    ���F4ŗ˞�zY�V��`-���K�-������ӹ��(�+�f��|�y͞t�<6o���8����}�HƳ:;i_� �T[��Fr!��Xt��k��Q|=B@VC���H�����'v�E-���C3��M'[�J9���Kf��������V,k��/���њ�nnqu�P<R����#�n�F��ট����7�	�Cq��s����׫�Z�ŕ���㣵Ղ� J�ʾ�{bb�Tt�����pi<��x!�	/&�F�����{V��,�A?�
Y_�j%��:�.��Ԧ=mj+�Y���>]�V��&>V����3�+�؇�ׇ<�/N�m���}~�V�zE'g9�T���-�z>��]�L%�F�勹m/=�%���*�b��ž�N��<�W�5�}���A�
.!���T�z|��6��/Kq(���,�ۦ�e��5�{�oQi��y��u^k�L��
_�g�f9�O7�����@�|>/n���gK�o�aʇv�����!F�N�j{0^�0{�*����4�����^A��
�<���0&����g��d����Ԟ���Y(�w�J�S��P(1��fg���;���S ��^�b���!�h��y�5x��".��{��������v�	ҽƼ�t"b9��Ö���;e�57����[�DY�}O3��FBpU�����Q�W�m��3M@���������uC�<ӄ:��.@���u
�6y	�'YǦ��s��b��>��Cc���"����7�dG�>?3�'!��:jU��
/�d�L6ew "_�^��w+��X b���{�����ʮ��z ���Qd�´��"���g��p<(r�ʙ2�nl:���>t�����+�&��s�l]��|���y�cC������0zn���K�P8N&�����oh����+c	��-�~�`������\N��쓣qޑ��O�M�r�7x(�����&r��Om3~���o�eV��NN�͔�F�靶I��M���sx�ax�z�r�����
Yf���e�p�l��O�cu�����τA/�ւ2�%<����(ݵ˺�5�������rJ^	��>�>�"�+q.!R/��DUŅ�|D����d Ը�ɇ����l=�_��LevKk2�����u+³P{񬎃3��kL���5�	��o��< �Fx�0�pQ����g�6Ѱ�&b��u.����دv_����'��mi%ҥ��=����BE%s9�B�f���\ޤ���O>�u>�Y�HT��#��<:�Đ4�ָz�F��	�0��[�}:أÝ�EJ0�M����g�_�~j�CZ�����~f������[�n����Y%'�+"1:��x�w�~�N��Ft?	�Y��R ���,�c���C��6��P���<L~��;"yO�0hS���Ci��z^�0�`{�V��_`\$�Z�{}����~�Q:=�����G?A����5�ۈ�����,�l�V�)t`T�דP��J���1#\��4K�깚D�;q�<c8S�w��x��|�oX�:~���H����"h�ǹRFo6�F�����љ�i������^ kK����z=��}
*
p`��g_�O�ޕC�}Ic~�ʥ�:Fh�������J�����Vv��G���*���I*��i���1���y"�@����2<����B��<�Z�z(����~X^��+4"��Շv|��nSSX��!X���.��qq��0p�xMW�8����(�w>�Q�+��L��h.�O�)*ES%��G��Ʈ�q'���߄p�]��np�L�|�.	�<��!7 �w�p���_ �M��y�i�Ik=�yl��9��j$�mT�'�,Jg��3w������	(��Yr���D��m�L�������A+���e�τ��!:k�M�TW
���ą1X�!�Y�5�=65}���9�_ޛ,Ch�H�l�ٕ*��s�A�/g}��=r~�
�N����y}��u9��]'�R��eo�h/�P	a����پ���X�t6�{�����|�)`�����O��c<�t&ik�,��䵊�|�z�Z�t�rN���m�G��B�Yf��g��t<I�����������@E��	X}�fv��(������O�`b�ߚ�-�ѓI�Oi��񱿏�"�`D�@�hR��(!v��đ��₟� =I�����o�h�{����f�M{[^�K���G�hv���&�V��4�!"-�Ź�Ԓ��o~�+�����������gJ��V�jO]D�{#���Ol:V��M��/��y%۝a�9;o_Y��^k<�����w˷s@�.nct���\�<��˓�����3)�I4���y�7�}W��cg��O���W��{�<-n�i�m��?&���W���?sw1���:�%eR疣����ZcD���Txyl^G����F=4g'�C���9%�z�Mc��P��ǵ�������m��"���Uw�'y����5e%W����[�U�*+G�����]8��H�i%��������5����������������>�'k�b��G+��s�c.��^.��~��G:��I��P�FH�&G��a�5�ϟ�Z�_8 n���ik�[�{ڽ:�WՆη	{!$e8_�NȲ��z��=n�b����8G}�+,��[[5H3���S �Dq�F�?<E�3)�?��A���6�݆O�S�+����x[���N����)3���W�[."�H8dQwh��XJ�F�O"����+T�Nv)骟�C��&��������Ȑn��Z���N"���Y(8�}!��I5/|g�������na�xb�d���ϭn�1�ZRSo����>�0Rn�k�|
m7d;��uxb�������r<
�?���q�� a�8ڤ���y~�NVlr��M��
񧹾�۸%�~l鰳��-���t�;�C��u���~����@�K�����6�ng&�&#i�g]�(��zj�#�N܁�V�Q6|���U�)�Q���p	;���0��|$��Q`�I�gHe����$ $�J��+�5�I���R��ע�j;r,�nd�~5�-�!�����s���L��R�-������5�U
3G�G������d��:rJ3d7�xӢf�UM�zL>t��{�|cS�0�̉�G���Co�/�
����� �
� ��_�9��cw=A��~^��;Wl3�ζo�.����:O,�?��a�.iq�8|�p��p���%�?����uµ'�t��	���uݒ�>�ޣ�:.<�ӝ�(��LV�O-n��V���t��'�X|��Ѿ�je����O��P�af,�w��'=������!-{o��6�0�5�<m������;%�8�Y��AW�}_:̶ �=\�
i�3W�����^�A���y1#���ˊ~��!J�Vw�2��[t�vX���	:���S���� ���J{�F��gi�s�~O��p8�7����	:b�����ӹ�������q	D��><�8���[��"��(�(��m���Q��!<M��]�_��R��H��T߻-���]���o$; m��x���t��Q"��k� 8?�`\Dq��U��(�7�
�^��{G�W�-ub-�L�[�����q�S`4�o6��"4�-�]���ER�Ap����)��^�f ,��!��Q'R�j���W�?�̫xƣGS�Z�{�1���y��7�.��JVP�����P�<Џ�L	<$�;��t����]3R�`w��6��c=Q���^>�Ӫ!VCh���� ^X(�C�Ub�&N~������0��N2 ̌�qPz�*����iq_�ǀ��*��J�
���.�E�X����2��>��o>Ы=!�a�h�\�����y���0���[��#����c�]��z}����OJo�z���k�
�-��o�#����W�.����� �n��l�Z�[�Y��qR��ݐK� Si%j��.�9)x!�    �.V�� &�U?��p���@r�tbH�_C��^С��2��f���f�M�L�}lMc��B�"lS5�aɰ��n�� �ϓ:�F~
#x�n�Aj���E�6X�%�?p}�7�{�>B�(�ѿ���Qre�1\��UM&A]�� �nF�u�qumۗ�wtϰr �V���)'/2�B�6A���]3p��Rn�H(7��ޕk���e1�^E:)0��)=�c�
�uͽ�wbr~�*�:��	��!���fej��o�_�9����-%�.��*�<�p{t[Ƒ������ax�j�o�.X������}�k]a��kP}!�k).����[4P
kxJ?����=N�)��L�;8���fw?���+�Q����z�8ߧ���
����[�����f%�Ztj�om���%�S��;ʬ��Wi'��'i+=�$��j��볏%jz�1�G�W��	p�B��Q(~�N��.����߅�:Ù 8��'G�G;^�0�V�����W�2������3%�jMV��J����﷼N��f��}bK(���΀���W1���"eS�_ri��u���J�i����w?jw��Ͻ;�'W�[|�
�h�1� �>0���C�=hƮ�X[�Y�Kq�$F�ӭq����s�<�o��>�+�+�������=e�]B���SK߲�=
�2��bCx��"��9�?uq.`���﹓~��-Z������%���ڄYT>�<k���͕��tن�0rpZ$����F����7���m��n/	3� ��!���י��I6I{ %�,j�q��F��Y��r%؊�1�,R����$�"*#�N�A���[/���?lߗ�WCϕ
3���j��&i�2�s%jO���k������+��{�0z�G���i�F�|�r"�"w�(��d�R��3�����ҭ����˚x~.l���q�RĊJ�ʔ�$���S��� ���
ʌ���\�������e� �����D(��������r�(�ܤ~��[��m�-�e?�i4W�αKn2�.��Evq�+�}��������7,����_lO<�	3���b�UV�Ӟ��҅KG���ml�֘hk��%۟ݱ�N�(����9���r����3B��������?4<�Ka&�P=����w�����?7�J��W}����F���8fW*����3�q��܅�a��A�̧�WwA������(�˕9|��{;v��̓�TD6��X�m�e��ɏF&���9��GZp�����M�U*Q�k
��(� ĵ����TB}�E��$�7�j ֢z}��o7oM�6wv�u9���
V���<>�D>`� 8u�B܋��p���u��t�|af��1��ʴ~(��+4�v�x!)3Y%W/�|֔�1�:/����"�L G�=�:2H�'�f[$��]�b��B�)kE��Ej����'Ɇe�k��GC��"q�s�"���oU��D.n>�0R,�:�pcCD	.��<�3�p
59y��5�=�p�w�"J�&��*�:f�[#=4[ne)�h�~IB~�+�:����|��A����+��9�#y2?g�a �n�K�¨wT��I��K@��
�ɐ���(KP�jJ([#vH��%�F���|�A���#2?�8�d�or+�$|"��l�K�O4�z��� ���r����ߢ�����ea��,���{M/1�<rg�F+ Tr�)��6��	��E?���83���N�nn�;m��N��R?�!y$A!��编�A�0�����y#������p@u����(�A,����g�!�����҃���$v��tA�HNi���r�=5Fu3�.�B�1DFYӨ���ÍPN�"l������{����o��0�.�C���	���J�� �A-�b�N��G}�$�E�W���H�t9���O�C��E��(%0¡b<mQ��J֤%*cg"��X �x�ݘx�/2�H�+�b�K�GZwZ]慼|����{C'	X.F)J��.�I��ş�/ݸ��T��txF�3A���Vm��q���X[\�l'˫�[����%�\[U���8ԡ�|H"�9� T.>�����K}�d���	����X�d�>k/�ԧ�N�����Z#h����7�R3ߙ�X+�z�nҫ�T� -I�M�䨨�q���k��'(!Z��*[�kk�%�x3�^���X����ܵ�7��G��R�2�s�G��n�x����j�!Ix�'�W��z�V ��3�9��]�8��q��U���j��^v��k?I<��ю��DC�%�i��bYC����$�0q�95=�����������_� 5��oZ�銐8pvX��呝6��r��(�"�|�=���o������fx1�aH�pag��υ�"��g�ѯ�����3�h��iQ#�"g+�;�
�Z*�?��K]?�49M��N���;�V>j��5=��/D��k��-,�K�C q&(�0M6��̛�Ц�]>�n���ᾦ���[yۡ��{����K:�C܌�d�S`g�X��Z���̒�YY#=iqf'�hE��I�D��nt?��/���[��n���d�^��7�z���(~�:���b�7M���*���ř���Z�����O�f��M�"���/[��j32�Þͬ�Z�����'�Q�
����@�c1�b4g��Ҝ��2$�S�3Kq?�Ws�l�)�J���R��Ne��:�KWV�5���̫�s�#���*,d��G|ۄ�␌ ��������.�g��T\��|��c+�=�:% R��잣���\��@�Ud����}�m�)Q�+�eE�m��l��ݍ�����2�sA�WQ폍8�� oC���x���>�����M��4|�k#�fU�r��W� �*��!d\hN|	J<���"�LB$��)�Y�e��m��YI��No��
����ާ�s/c��f�,����N�N�hs�W��hy�S�f-��EU,�LKl!���1\FȜ�eĊ����|��s���;�*���y����	E��7��k�9a`�1G<iV3�!��G�2Gr�L�yl��h�W�q	����po���;du;���ޤk���В��?�Hi� ?�W�#�����"�7S�����|��O���f��չ��8��Nǒ�I����.��bqw���f�\�#�4��\<����>���i��/dQխ]��냝��e�|���H�L�������"L�
�;<%��w��u't���Zb��x.�Q�o=��gb�-�����/�VqЦC��{�B�߽t}x�g�h����G����wA�R���-P{��)��>�9����gB[�H|��(�*m`ԧ�6c��%�MS�­��إ�!|�`�ӓ�a�U��c��.��b�SE���n��:��|c�6�D#��4|�]w����=S=ix{�o�S��&��[����S����z�!�0�����izq�_�Uۈ3���į][Tѡ�d�Ҵ������v���T-b�w�UR^��trq�zJ\ȱ�(��G���b;�q�u��;���m)�������Wt�������@yխM���ҿv�궧�|#CI����5�&*�;������7lR�HTE����0et�-7�NIKi,�3Id�sپ+~\�>�Z����X��|�)F�b2�[TN�4��n��g�?_�#����g�A��ڋ��䡛�Ô�h�Q��u�m���%���Ҫ����{�'<��������a��_K��4�Vp"O8��Z���5څ�m�l���8_	�fs�֐�᝵Fu���ݚ�9���������Cd,�~M��Ǳ�#w��N��Q
?|�cI0q�#�Ʃ󢱥��hɥP8ǹ��t�Y�ul�����k�e�6�܍��#�B�Q�^��Z���d�N��&�v��u�F�_�,��I�t�J"o����h(��X������1��o����Ǚ�x���£e�v\��+��Q�~r���DN���"q� ���CyХ�[a�H_��1�2��f?_c�    �#�Ԭ%s��]Cs���h��P�:%���2�����I3����ɞ�����j������<O�zڢy��������;9�����������/&a-$���%��(�I����Qo�W-���c7N��I�c�Ucx4O>Q�Op���Q�B,��X�4φ{�B|���(c�
���?�*��<i���"�6�f_��J݀;>Mw�i�\����nQ���ғ�39�K����y5
sqW��)1?�ZsΤ��\�K3�P?�֫}�U�$ҧaxb��&\��i��L�O���<����ֹ��i�`�1�-�rb�@�2ؒ؟�Zy��J�����Lo-=J���aŎ	\�&��{6l׶�7ۦ�2�W�jQ��aHT�C�-��E�$�(q�VuNf��f�����1���G����1��N�8����nu�AXcz^�ǆ�&��Nx�2�BA*�*қ@��x�����H�J}�^�/�1��{��oyaHJi����L��ݾ�(�&�G�$�n7\�0X\1nr�A�6��$� Y<��/W5�EH3�pw��$</L�l�L+�\����(w�ܒ�)ۆi�����񄨜z�vy5n�ǔĿ�|@2g�f!?x9�m���bȃ��YCDt]�!S�/�V}o+�&��2�Wf�P�/*c�ɚ�Q���k�A�o�L�
{�Ϋ�Q�L�u���F�B��������o$M�����AQx�2��G��(U���I�3����q���`�fA"e�͑x�I�=E���V�u���dbfn7���7��]�=�mM�����#NSF�"M���o������f��S;�{iY<��;���� ]�ߌ��OA��ʕ���7���|��Z���W;7��kI�Z�X��V��E���R4|3� �;�E���}_��E�L����Yӣ�����(p�=�n��|c-v�1�C�Ugw]�n.�A���^�4~_�y�g�V7wz��sH
��xe��(٧��+��uu蝣R#�i2�]���=x��!��_#x�w�L&�C^&Wa����=�G^7�3q��N��}!Efs�b��.�k���{���'K"1k��J��
��E�|)�A��A���L�-}ifB�۞E���!��:��V터�E���:�5x�or�3��q���Wj�c���A
����8>�$���G���؇l�[�`��Z�����$�dv�y����nfɺ����4���}��3��]Q�.D
���?I��yN�{�L.���4���'�{�G��twv�ޒWy]1�����{�m��x���Y�Ct��T��ãϽ}��C�4�
���cM�8������Z�N�����w��଒�7�h�Q���,��_ݵ]�C�B4H�*�@�9����8�g�L*(՜���>n�Kd������ͦo��h�vE����_��:�~���ea`<����p|���������U�5�L-��|jfa�Q�Y�t�<�1ܮ*_?��Ԉq������?���n���}]���%�-�|@�����A�,�b�fnA��?J0�}:��c<�\��F̴jWK�CKU����B�h������BEؠ���ZR��xNt���M���	�=3� �ok�/_��	r�t̠{ڳ"_F��D�u�b�RK2�����i�
,�r�X���� ƅW�ZR��P�FQ\-��¾��'s_�`�F����i�J�uzw��A]�A����Lytl���JZ<c|�9�x��Ф�R�4{��V`%2]��Ή�r����C=T_g�U�{��.4���^��N�q�
\��8rqE��n>�X��� U?M�G�&�K��L*T�(zt_饆_���׳��w����J"[�>��͵[�k�B��b�����[|�P/��kH���mcρ�4
+�Wם!]�jq�I![kj�����F�n+$g�z�Hd%m�F��Z�7��� n��4H�O*�v��7�S�����5����ģܙă5���ܦoZ��ת�������|Λ�1�}�������=��������x�B��e�M��U�{@T���]���`|��M�<�y���F5������.v�_x%�#Or�0C�����tX�\M3��GϤf3�ʹS�r�����n�Gp:p�3���@߼�ӓ��}u]J��D��0������6ƣ����k:���H3�о���6)�>��s�vo��.�cy:���m�{��&ϡ^��Aa��AP�Z!��m��ĺi�5���}b��\����]д��Y����ք��y��h��P<W�f����!LZ��z��9��8�[g������t�󏟤�?rYMPf.����ϟ`�������zؽ���Uji�=sѐ�{Xw�#���f��>گ0������_�p�cJ��0���W�5h��<�g����;�w��^r)����>��q[�g� ?Iz�F�(Í<>���_�$�����(G��p��z	�:gD0��AR��L/L��%*qa�玸��m>ikf;tۂ���{x�r���c����Jqc.�'/�G�:n��r7����?�*�����L2�xl�����p�G����{��8}z�ɠ�?�� ����v�����L-�d�N��/���D
2n7};�5m��`f wC;vq\��Z�p�<�Ħb��HZ>?;pO�P�;��6{2�\,<���*|�۲˾�vI���OS�?���^�U��/f�At?��1���r��&��冸�#3�ä]��|R@rt�m��Uv�/%>cx.��ErD�.�34ɟ�_TG�0S�F���j��%nը���&6�n<F�7�����[T�k�B_�ͳ�j��%��0?�?��z�fba�����U>c��YpZ:Fjv/�x��~:IƊH5�VN���]�ڥ>㸛� X(!����5 ��+%�=0�
�N/=����:��ܞRveu=S��O��k�+�����7��z���w��S��y�B܇��l��db���H�Ԃ��P�����lsޜ]�Q���59T�Z�!D���]����(��w ���I��.���D���5�u�y`&�$����l�<l>�o�.]�K�rt���m�x�'���H�b�����k��O��d�0E~���w]MF0Sͳ��e5�&���K�D�=�sy���^;�����eH9�ڄn����)B�-��8������RR��E���6�/�h��<�d�}�]*��$�ؑ�6]�t+O��=�Y�\X�Gqr�������E0�݃o2颊~- ��"wf��3�kT�����d��s�Y0S���dɦP��v?��O֋�Wv�xu�����E�����c�f�� ��]����A9���1�{")sҋ�}AN����~������;'�q�7�Q\�n������7��K:zK03�6�k�q�>��k{�:��L��v�tIs^WK5��d��S�S�ݼ���	Q+�Ц��j���'����Կ�]\���^f�a�o��^�Mխ�·���h�x��Xy�;�b���qL�[��G^�<oc�J�~��A���R������i�
F�ޯ��p��gh�����ƈWd����F"ZZJ�z��GVã[݄S�M�m�L�����w���P�oТ�s&��3��f�a�~��,��b�Y_���=��z��LVcE�p��&=��/%��;ұ�q�À$;P�Tl���N��
�A�:#6m�J[~��.��Ѐ˪v����e��\�]o��J����o�7���"C�☛�k�k4������{�9��̤ä��gyr�R��5JyK.�}���r�z���8�t�E�M��]}��F���B�+;Q�B4��t�߹�]� mg0SU#un������U�eL����n��t�o�z<�ro���^Q��� ��p� ���g͙e���1g¡�-�f�o<}!�$c'�>�`�g�=�ў����7v����v����H.V#JbP��M��5��	��8����(CI|3�p��K�
������    �J$#�=E����R�ϴ �[H�})?�0����`��-��������]`��睭�sy<��8�����a5&^܋�%��_�ze|��i���XEDb���SQ�*��H�����z�[!�3�:�h7f�[ρ���zNC��A��ݿ���l��F���m���gb�+�N��W�G��A�=�#�0��Y_���8Em)�S��O:�4,�����W�؈mݒ5�?�>wP�vYF�L�1-����_�po@w��N��vk!s��e�E���ҵ?��p��Ot��p��:���-���(��Z!�	T�-@��N�ȸٯ���r 3�0��\x��粗���+�m�(\���:�y�R��w}^n���
c�>!��!@AN�^#խ���T�Ԡ���;c2�M���#06���xyZ�jM�R�Z�FO˅,���]���wa�9I��ė���}Ӝ�������Sդx��:NpjQ����f�A������r\rS?(w���О�Y��Hͭy���`���f�O�{\�!oq(P�g��H��۠,U}�L:�F%kcO1�V,񨵚�̦�$P����N�U�6:����z��K��� J��P=[��Z��������"WD���2<��B�:������	ȡe�Sq���)�x�T4ݸ)3�v)Eޑ����'�c�,��U��h��#?k;���
2lJ�&o竴�
��x%ԧ/��[����풝qdz8<����U�`e��>���s�u��Q��IQ�	�w��|@j�w�K��������g�j���ץ=���I'���MK�*S7.i�E��r��!V0��A��v+$�V�50��6η�y����>/�2��#.f��+��7�E��c�a��=��+�~#�����/��k�᧩�6~]�PG�
�C�y<9Ψ��.��VO�&�R�O���R�̩���Ǽ�K�me.؞0C��%�J�"NcX���E��5��ߘn״�v3�P��@fLt!_��阰������)*��9��0(�I��2�".���ŮqƘ	wI	n8|0��j-�l��+�)A�]�۝��!���s�'����{��0=�����X���"����>'�z���qq�
z�����*�F>̬�r��Ge�Ua'��������|�r���&���[)����w���wF]$
W�0��J=<:���x�7�7����;%��>�*��n�V[�Ή1a��Iܞ����%�1J�ѳ
.�W��8�(I��{^9@�P}��q:�GP68���&ND�vh�83ǰ����|�i��P�F}T�M!>�ޔ�ju)D/�b-}���/I�9B��	����f���QR�rf����d}���PrZ�x4ݚz�>u��A�/c�*� ����g��h�$�0⬏Q)Y�� �Bp�k9��WKq��4�9+g�8��]��F'����7��<�}|�^��:����Oi�A��)���B��5nG���i�5�/&g����R'�8�������G�~�EW�e��ޛ��^ݎ�Ū���5FY$^����`��'���߹��6	��:4�������uB��ǝ�NΨ�pݧ��E�[U= &((�S��IW�3H� ��,F
*��)-�G�I�o��2�QvE
g&���P�d+�u��Hǋ_��]���E��¨�|�k�sQ�f�U��eaD\Ao=��pp�����UC�>�3s���������ݓ;?�D�[�3����RDs���%�x��aI�bT�KC��R{_����� �93�������4�$p��c8��-q�lx�ꌣM
�A`�s�m;�#Nx>�Z�(<��G��/Q�2�c������n�Y���/���f�N�Ai^ļ��~��&뭹��<���zl�u��]�K�	����t��Y�=���3^���G�=�[���G���/�E�X�DJA�Ƴ0X>T�dJ��O��E|O��O��O$��O���nx���ؙY��T��M�=�j������7���f���A�ԝ�	�����4�w�Hh������]��q�\�{x�83oо�n�
峵��Ar�����0�$�E�`_2�f򫟞V�S��j9B^��P�D�̓�'�_��~�����͇�G�2���3St��>3U+�1;�^4���=��<Qԛ��� w��J�A�}�����P8 �a��Ƃ%Hr������a���tOg��V�	�Y�Bm��=�#O�����^o�ח�%��\�~�^o���Kc����b�\)�޶�r��y�E�83��vn�}����;�����:*���nJp=V��d_��>�l�6q�!K1��'.�'�m���6�tB�P��oUK�m���LB�ԫ�~%K�!��a��R�i��ɽP�ތ��l�~�=Mw���x��"��Ī~p~'��nc�Q�;����w�=��yIU��2��<hƭ'��UH.|��B��u[Gͭ�r�P�5�ø��f5��ec�x��3�	a��z+���f��-�s%ǂ�4gk�!+CG;��Lb�d�~W�8��7}��տ�Ԩ	��I~�t�}�Ο�0�
,}��U��؊��t���1��H�C�oN=
	��$�K��i�w���308�\��)W���X�H��hdf��-�x�b�b_��ۭNaݜ���q�+c�����Ƌ��BrP�')z �h��g\����'{�X���$�I\�����{�E~�>��Z�s�6BGF'@��N?����a���+��þ�Ռ�M'�n)��@�q2 ��7s����%�v���Q�#c=q"kbY��AD���rE��~[	K�3��$:�QCg.N��W��S���E�ܩ��;3�������&<ݾ��;�lǔ��u�ݽ���%�l"k���4�����Z=�bm��V�-��������Q�Q!�4e��k���Ɠw�����"���\o�|���u�c�h�E�Yi|��p���T���������Q� �g&����A�λ|�#kZ�r�$F�e�E��Q����N+��dif��Q7�;�=v�vᵿ���o�f�S���|f�mjԓuT�e����Ĵ��n�t_�S�TE�T��aD���#���fq2 JӀX�Nѵw�G��w��t-<�g^!��]7�VK�'}�����M�n!�s ���.��7����r���H�{��aTG��ǲcԫ]r;3�pRW'�1�s�v6H�]`���P,��2��tc�t�^_w���'�4�w)�C- �E���i����He���L)8�|{�k�;�ݦ4������/$�{��I*C�i���ӶH���7r�������Z���B���OG������ouć[R�A�����Z�����<�q����']��H�0<����oT�������6n�ш�3=��W�Cf�{��j��T��B;�"v|���Lv�Mt/����{��ω�V f�2u�`5�W��~� �9�	�M��EK�!*<m.G��}{�V9øI��}�@,��ֿ��Y�F��g��QID\�lg4�v���ꆉ�֙5�	����C�/fOW�pݱ9���A�J%�����uid��(B̆S�,C��E��\j��m<Zݿ�:��D$�5�	{m'Fi��!�9' ��%���m|U�Ϊ�~������_v�����c��kԊ��H	#�Ѱ�E3����ϋ�s�ke%O��׾k�+[��J<��q&��>�F���{�.w��!�t:y	E;���O!1�A�5,Б��@%�.7A�L�5q
��j��o�9���B�8�Q凗k7쭛{9����GB�`x�0��G	���$(5!ģM��Do5�.�K{b4�J�� 8��5{�逸��pՄnm��>=��(���Z:;�$�Cv��8�~��m�:��J'p�eS('p�1,���+{��7�A~w�C��[���f-�ѻ�T��N�Gn��C�����rHuF'H^
����ƣM/��Ǝ���o	�=�	�!:�TFocmn;ҭ%��Q�����G�x"�    �E([�G��e��lk|H��?�E�0�E >�;�6�r���LC�G!�'>�
w�1t=����Ԣ�ߦ��U�����fK��H�z��~%X0�et��{�!-l����e����+�H҈�8��m\�e�턃��n�(�<;:d�ԾG]�}�N0:�zW����fW�H���is$}�f��%Q�ݞ��' Ծչo=�^�r*)h6e��\�Ҙ[竟P[33��2���kJ�ʗ��VX�m{4l� �����4p�-)�G"L��OH_lԈ��l<N��凑4Us���A��d����eG�p�ؘ�cY��~v N1����a����-,���p��+z���o���j6m�u��^��k��M����B<xY��+��\�g�z&p$�R�7(:�wY#���`��Բ�h�%��O��9+4A
��v�ݝk���'�	|���T����t��r߄��{�?�7�4R	Cg
n���B�l����_�]�G�eaO������N��i.���ϗ@�����:?�aN�XW9|�46dw��Ma���<���eA����h��0�$����%HӞ��[|s�Ӫ�_��WҖ�B�)��pX��U�%���_/���t8p�W�,���Sr��9�c�#D�h�t�|R�hW�]�#~�xbE��.�A8�ln�N��<���=�^��E�ly_[��~��1.-e�e}��(�	����۞���H\�Ҟh��EѢ}�V��[�Dg�.NG�V�[%g�JJs]���o�m�M��g��<������W@hC!t�7��'B���퉂X���`���n��iBZ�
�8�ŅQ�
��3��+�t���>~]$&��>�f�' �Ѱ��M�oDi���@|o1Ȩ<[E��+�|�։H}r�_� ������EϷ�;��	���"���g�ɴ�B�*���i�o����-��'��DӇ��-u�C#�ʕw<���˽x��%�1K+�:j�m%�ʕ匯W��[�&\��Y�KW�i{�ɚ����~I۬c�Wi��=���?����=�RżW2���������_#� y�<��f9���-���1���h������ �*���?L�;|R⎳��+c}�\#������C���N��R����JY�<f�C�k� �!y��p��jm������BZ��DDX6K(+�����nItp��rggoF7;}zJ%)��*�k6X�����L������a���r+�~q|�q�F���B,��[���Zk�潳k���(�֩sE�Q�|Ȍ��m���6
�Ӳ(g����uo���������ӏ"Nl�^�V����V� ��?�O/����H~SX����R��G7�|Y���ξ5��w�bԆk��(��v<��U7Xf���;Gh�8ߎ��XRE��[W��T�lns��s�4�w����ݸ�Pj$����T�ϼ�v�@;�Ɵ��yHD��퉌�ݵf��nk�A����� XJ���Ry�$���������~�wx�pK�֪�f��ڔ�h������<�� ��퉔 �~���pP���x�d�A��~��V���?xw�沯�ތ\)�>	8t�_xA�*im	���agS{O�&�"kQw��	��`� TuS�R�j��ȝ�ћ���Xc��W�K�����]�3�ʏ�ę�A�Lk�ށ����?o�jM,c��~'L����ں��m{�-Ӱv���Z}ʸ-�m��F���a�<�O{�����8�3ld�fwV~�_x�%=��싲��97q����j�ᐽ�7��GR��}�^g��W�9�׮0l�5�dyr,2����(_خ�i:%����������J<���B�z�}`tO��dL�c��]D���\((�5߻YQ��HW"!mt��g������inz�9#�A�K�[�����8׃��M��7�eŤk�bE��ޜ���̕d���7���ƚY��L�^��aWtu��:�tVQ��=1I��a'�yK�cO���m?���^��e�λ�/��X��cq���A������LKԚ�
Gî��w�+L#�'cO�D�w�m8nwE!�ٽe��ז�-������{(\�����Xd�F1n�18M;vZ�1�  *��F�n~%���t ��	S����N2#K�����>�o\����zd����I�k�����1��� �5t�X��~M�]4���dE��#q��s�8����R|����B����4օSV�O��5d��v��%]N��H*2F�U���8�Wf��n�W���YTGaϐZ�=Q�[%��h97����ڛ�%��͚Уۮ��;�"�~����<�I��y�����f��������ǞV1뭠@����M\H:���Nhwn<�9���1;�VB��F��x�AlU˿%v���R�� �of����{�R��]��=��.)���}�Mt`E��ϓ���*U�^�"�h��_n�ڌsL?���Ox�����LզN`!�!�Nh!�H��3��skW[�>?�'e�|�M����xw�������h���ORpJA��,RَQ�eP�@�ƙ�h8�m03��ԙ(��U�71���f�G1��R��5A�}O������'߭]��Y7{{�BF!��fr |U��X ����@g�$�ՎΪ���0H��G��\�>�_��5�J[�v�#�O�);�k���H�n��^�kQ��A�� ���~��"��Z���G�����X޾�thLCJ����̟�[��w}�H�@��ؓ/�J՘{����c����ũʿ�ف���zg"#�H{�VE��5D����R(�$]����S�Dr��5��>Ə�Z���S�G���f�p��oU�Q��G��'a�C�7�b�⎑����0��G��k��gq2��`��!��B�]����2����l�9������'�z����D�~�]�WR�`��s�VlE�2����״�-���%L�}����ٽG�=������#,��4�w��j�R"�ۙ8����1/���R}Mw�Z*��(|ڽ��0ܻy?nU��;5�0l�����%V7E]���(1�F$�O��{
x�"nzF�c
](Ї����+�h��\�{z�x��ؕ����qc�iy��u$��4E���9⛑/2��v��A�E4�w�6;T �֊u��HZ��n���z�>��u��������vsjd�ڬ��������PJ��I����g���H�DD /;�>8����;9%>�k�<��K��pTg�~^���C!���H�R��e��,�j4k���0|�ǚe$��L�Ö�wC��ɭ��${��G�g�Y�Y���F���G�	K*�.�T�%vHHK�,P��}���gV�G��)����K$��L���ת�fqD�T�Ќ',�m�K����.���|p��T��E�g����=u��p��������*'�L��V�v��ù�o��wU�"q�u��;�]�Lה�Hӝ�y���Go8�g��\4'�H����I��1 �D=<��|��ݽ�m�1k�|4ٹ�'�^�d/�Da�b�}I�vŴk��"*��٪ 0�0m48.�~C��Q��g9�D9��2��6�-�g��`d`�^�uvR��Ƴо����������2>JC��Q��9.oP�6�pfkP Td�LCs�-Pk��;C��N]'�h��߹
���pzy���>D^����<Bb��H #�J�F���s�H�z���S=�wuX���n}3|���Cm�שmLB���@�l�\����o��<+
�g���X�wh���㨱B��� �5�ϪE�O�����n(��37�yK����e��j.~f��p��S ���e��ڗ��E�r=/+��{1�p�F'\$H�~J�2�#8ِ�pO�W�`�A)��<��M�$�\�>T�f5�sz~��q���C;�
��*D��[͒|4���.9���kk�b�U{]جv������ ��{w��5���1�o��=�i��XL"G�r�����wM��r⿮q��UfW��    3O�}�������6��6�vo��`��ᔙ�N�Gg�B��o*Q�s^L\��{'�G[Ճ"���`�Q�`B;R���|�I��p��dU�ϴ�&�2c�|����{�N�ÚG}��.�73��~?��.��!�ґ��"���L���#�~�n~���C���%��&�{�K����
��yn��_p4!18����f:���p���{1d%�h�3�����c~�n���}m��q{��Tx9f��ܸ>N�Q
J-������� s����v�c9�"��c�� Q?�>�3ODÑ	.s�T�C��g�
A�0h٭��~��m�L�N�s�l�1t���HacXtN^A�ģ�|�Q���8u�5�D5����MX��'am|6* ?�s[�b7'X2�bg���\�FXA��x�� �-K��XI N����N���y�C!;����z�~F,+�k
��� ����┢+LJ�l���TĢ�V��h8�_d!C��3Q���
~��s��؀83�p?W�<�lr��7_dE�^��2��%i��8:G�(l<:�"�̟��e�9���˫��Սڜ���=&���w8C>�Ol�@|�Z����
��1@�F�I�UL��=���p�Eb��� @c�L����gTν�ӲPs�?�m�ٴ�������U�Ƚ��ލMgp�x
Mɑ$<ʂ�0+gE8NC2o�i�Хw&>a�u%8�-5�4�RZmv@Jʕ����x�fu����E{0��7��H$ۏkRbxP��8N�(LԬ����S�f��(t��҆Ld��	�U�����;�z롒������M�-2�*�����m���U�쟶�N�W��yf��p&��XU��k��`�K>Wɯ~���aV�-Ic���[_ɃSf�tK���A��=.�0{�������ז�N#����Gxo�]�3ѱگ���p���f����D'�x�z}�^��UJ�c�=��,��Ϣ�7��Y�GgX�#��hz,t,�J��DH_M�6Й{���(۾�Q���J���.����1��6k�c-���
N><�z�.�j��hr�V6^:�c��0��D!�H'r�����*��G�ʛZ+��^4VT$�Mquc�6��oU�	n�D5���3������9���cj�� ܢFמTE��b�D'D�D�,OVkZ�^}::�瓦���p\�����/-�w}u��5�q�
0 up�9�$)ҠF#�5굌_n�쫸�%��yw�ڝ�ԍ+�Lѐ�Z�^���Z����Z%a���Z���<��*} �{��*��̿r� �3�`bR�ؗ��~ʹA�<���T�n;��9�9q�ϳӪ�k���*Z<>%*�J��u߬P}9��?`���a#{|Ǎ���L��m�}�O_��u��V��%���(�%vW�i��J�Ϩz���dE��ņhY#� ű��"5��(0��/��,��X�׊T5�[�2?�9�q�V���_!�����x�4���� ��!N�M\������E������L��F�dD�rU{:�>�*ry�w�1�P2G-=�����R�Z"���J��1*)�� �T�=;�+ &a�}��|�#�K"�����j�ٮ^�K�4��������o��s����b�m�����s��@��F-KP4�������'���娳tRzY���G��Rn���WF�i�Z5~I��gfQetԉ� ����@"��Ea����?h����nn���5��=�v\�+h��W��&���5z��}>�n��ꀿ@(m z�??�����FO
�F�@�z-���m�T�Z�m�SC�ji]m���7��۱��Y?�I�"�K�yB%��l3��������lA�:֩��x޾�]��%��0ҕ�f<���l�����[���1M�!��0j��)7�gw���*4 �ߪ-�x�ty��I	�qq����r���ym���R�s����l>$q2��Ws�=%ή5��q�q�)�����F8�2�Ǡ�G)#q\��>�,�q�;�;�<�AxyY����W�X�i�4�K��o���|��GB��P��S�j;�# ��J���'7˱G��܌O2�}����Κ �z�(�w��B'��^���Y�%w�+"�8��ןD�X#:F
��3�]�_��&!��zg��`�[�3M2b��\�ʜޛ6���]�<��ƜK�|y<!�&,`�#���� 0��x0�k��[B
��E�����k)e�۰گ��nӇ7�s�n�yq�
>�I�,��9|12�i��Tr�� F�������E����n�&᦮_�s�Hn�H��Uv���%ٴ�:Wdk(��{5ʵ���z�5�f|T��ܟ�`�^.}�E %Zܖ}\A(��<��m�P�}3եxL�(K�A�P�9�y&���9�)q��up����E�I*^Wjx�٨/;��8�B��i���o�J2[���խ��ս�ٽ�����rNío��ar%�qFnvDYpf��C�]#:N"k�A�7�*�P0ڃ�D0xa��Փ ���Uf��k̅������U��+��'��z�X���m䁖
�����c�x#��aD��3��&A 
iN[n��	����c�,��������@)��S{L	��	���1��p�v塀.���_$+���rڟ<�
�����X�U�j�Y������K&&��"Yߍ�.]R/�ܴVL+� �}p9Z�^��s��@�N4 �J`���)�Fr��=O|��l�<���9�[R��6��7R���}�~����Q�U���Xe��axf����qy�7 �OY2��1j3q
�Y[n�������b�DIMpE֔5Yl�ι<`���.�D���G8C�� +�p�46{����H$�RL�����)G�9��
��>۹V�5_��}�ޭ�n�lDԭ�`l�P?YLj�.xQ�{4�/��"�G�t{��&�@��w步)W��R��{6(�ꖸ.�Î�i�
�b����p�j:v�}m`�ɴ�R�Ɔ�r�Y����Ԧu�����9A͓���8w�ٛ�<��Up���uW�ϫ�I�i�l�#���-vF�zE�j�3�I�?(���JA���|��bОZ~�*�s�"ОS�U�g	��߮�8� �{h'�V�E/���#�Da ��t���O���c0�u����i���r�x<���m�m�$b.�`��n��h���>�r~����.�P�3z<;����&��L�x"���@}&R�.�@�9\�[�e��8�凢;�;�-?~�z�C2�볣�;C��� �܇U��Z{4@35�( �Gr�`�x��~�x����U7]�[��ݥ���ԁ݉��֧h�_��j�U�������\��h�v�X����Goܚ���9
�&fa��7Orݵ��2����@����-�n��*�U�
����z��"��@�U��,���E�e	�;�B |��˻\ ��C��.뚺����J�W�p�0���z8�W&�{����euP�7\C��c���T���l��j��Zp���|J�y�W�v_w�w��������]���_9�� a���d�]KB4�"?/��� X���l�)�S�H�2��剼�D�|�Qa��e	)R���X���q��cE���݅7Cx�'yh�jӊY���%����S�Z�r'B���tu�,�@NԻ}ӉN����{�l�IL��J>ur;/����"fƷ�n�\���8Q*m����N�&H@�Nd�e��������������-�:4���8�)��[�ד��r��Xf��o�%��}�c�����{����HN՝X=b��;��� d����"ho��&<8덡�U��˜��U$��F:����:t��"�
��p��S\�zV�+-��'V�)��K�t���v{�>�>i�)>��6
qH�V��"�F��_XnvȐ��o�Q��eu�2��f�q�.��?}+�>8�'���'��xp�^ބ�����ź��^���[�8�u�2���ߣCx    �B�0|��h��_�p�6��D$T����<90���]�|�f�W(�r������\K/R;n<�}����
��������~����O-�!P��OJ�e�	܉]�ҳ�vE��Od�Q�sc�������}�(�%���>���Q�5j=>REDW̨K)2\a��ػ�B=�L̉�6ˏ]�(�s�����J�����|)�bFN��58�'L�q�kn|`�k��$�JB<�⯦f��|a��s��1Ө�E�4V�T���Ъ��V��������u�~�R"��|�c_ˑ��_2�����l�h���N-��Ҫ�ڶw�9{������u/��g����8c�<\@��b��|k�#��0>����[�Rj��]��k����D&T����0M_HJ�)+��5�#��2�u�G����m���A��	D�����5�b�׾�d�֟���6Y]���^7�#S���R�?���1�.5Oz����%�X;$������[�hsDE�
��DT��?�k���������� ���ұ!����P�e�D]�ǵ��Zç_G�γA�B�|X97l(�y�r�����u�L-+�a	��3����ODCz4����ڣz9l��#q#��ge�l��ܓ�����j��GV�x���(~���t�Qf�����[C����(���Q���&��]_�BZ�o�1��7|�{.J	�u�	Cka*@T2�:�Ẩ:��!�C��r��k������cކ���3�ik��D�ޫ�IUK&�r�v��K���w�V�m`��"��3r>]�{,3E�!�0�NlCx}��&���ٰ��L�˳v`��sdR��ק�'�O�+مqXt]�b�ω�g��8�}m��Y���F���\5� A�؝x���(�l���ZqU	1���J���f[�?g� \��7PV�﵂�>'�o�tRY������r�M��N���9<c+w�T�M5�t�S��/��`ٍv�����I�JrN�}��C���d�bxē��E��R�C倸5��p��S�� �(�ӝ�=�m�`}������4�S�h�)������ҕf�o���j��;A��'�F�� W@��j����F�O��"���*�OK���w�C�������^��νx�%=ftV�(ۏbI��ǌpG����dNS���L���/7^�g�Ѝ^�Sޝ(�#�ّ��)ɍ���w��SJ�~>��+����e���|�b��Dq���8��(�Ue��@�7Y4���*�&��j��ึ�I<*���M6���Ӿ����y���^x?��N����:��q��gg�߄`,�f�7�n�hA`�������ߝh;��6O�6$;�7�n�)��ղu�.}a�s��~)-w��q�'�� ��C|;`vF>̒�n��:�Y�>��b��H���)n�ҫu���w�}�uY��@����l-���o�5lY,������O��-Q�2S��l�ǣ�/J���G��b�F	��D;��wYA�μ�[ƈ;�,_J�;�ܕ@�y��"+|�6U锇n)VRyT�6B�m��ġ;�5�� �4i��9�jRtuïv�U�����]�����e��k�����#Ͽ��(�P��^��rn��[,�B�Q�lt���#w��e�><���f�/�0����ns�V��6'_�I�>�����r���Q����	 qF�A/1n�����"�X�B���;�����)�aqZٛ�$��q?��۰�^/o�4�%��dT���I��A�G��-��VTg��z�e� \�u��0��������]*�<}&�����mER/��2D۶)B�{�x��o�a��)���1W�g:0,w�$0l���2,[��|Y�@��TpAr8���Z힜�<��_wnE�	��P�8�`���������,�dd���ix}�]���y��-�D�(�� <��e���JCyT��-9�M�ݮ���}��QJ,n��Xn��IV�������K��_B����Jjaܑ��W�v�����8�&YFR5r�ٯ�9 ��cF`!u�>&�9�aZ���n���>�	ST�;���}=��Ftʪ��ZH^g�,o��Z����Qy���G�~t9~O�F��t
~Qģ�n����=�2�:w�N�g��2��Q���Z�i�����,��B8�T���R��� 7��t�V��fzf5s
4��	�BS�����,��;q�������Z�拾+_	!��/����ur4��5Tf��<V)�4�g\��÷a ����l1Q�*��&��)��[}�ϹQ��;_�������}��B�Mw%	*��9�ׯ�3Aoj���C����F"���D�|ze�$�M��{Iy��Y$[9q�Α����֋>[��.�U�M�I��ks	!}ĵ��� �ӣ��mJ�,����&�A���>��+��p]��!U���W�v�~���������K$!�'�zf ���OJJY���8]����|x��}]��=���Z?����g����<8���K�~���Ou�-2�oVH\7E�h�����]��MC�,S�����Z����P��x�ȓeپ޴����ݲ�H,�y���E�9�>�A�����I��Z)�B�Yz���zҭ/���g�y�vꠅ����!+���T�Y��A�M�?��Efv=r6������"�JMN��Q�R��3w�~���TwA����g'rn��r�ꁟ�xj�?�UA��,��E�l�^9��� ��g���x�p��y���Quf�ȆH��M^���2!`۫��'ĸ����+���2��O��;#9�y;޿"�^	�-�7��RNdָo' �Jo�7U�Z�����B�V��^��k�;֪�D~v�X���L�� �ႀ��$����qj�ŵ��D2���*b��������?����%�>~?�p����*(���Q�ۅ׬ �Z3�@a�U�X��/O��=b�Ko"znժ�z�i����e����rc�)���K�����9>�m�^���Lg7�̠殦�p<���g����,���&�A~G��|�Je
n!�H���;��e۬~��>�Q���ݎ�/ܩ����;�d������
�ߛ��+6�&j�
V�J�=i���������uMZ�w]ܓW�y,���{Seo7'�Z	�%jЀtL�ҟ�6<gaZ6��L�	�J��O���{|�ܠ�n��1��E�!�����k��r�OC��YᤘѲ��-Q����u���˱��E�רÀ�*ģ�_����gs��p�+���ۨ'����+�9�������Ӛh�f��'vD�Z�m��|f:*K�P��p���)�6-�M0e�e*��U�˗��<�|�q���
u�[;���޳9c�Fy�����NAbf� X0
��q� ��>������Eef�9~Y���R�&Z�kv�gj��f�������,��� d�#y���o��[����^�����PB�gi1��� �`������N
Ն���&�hOɨs��������m����[N5��dO.���|�A�I�L��uz��"�D��'���X�,&Ja��>ͣO爺����aв��z��P��+���j��f�GU��q!���p��Ď?,G���ij�ܽ6�ީ��SH��Y=�|���"�[@x�!��rw(z2�v�^�̜v�f��q>�%a�Ig��Ҭ��+�����-q{E�(��-t��W� ��W���H)D���.^z��a��fx��[6����}��?	D
,�L�T\�/�m�tJJfjێ*��H�vE�Y��!<OOǌ��:��_�~9�r�߆c�n����$����$�R7�·w[�@�U3������Nu�CO���&J�z�v�������y��4��T�5R����n�ՙl1�=0]�
y�DIy��(ʚ9�hx�"@镨AƏ��O�����4�+�_�g��W�F��ǩ釗��xG��}+񶈕�L*1���ϊ�h��,ևv~�0����    �(w�'��I��?*�m��.~.ʒR%�aE�i	�J��2*�rIk���������>W�;-xfz�Cmc���k:�,�w�y���:�<�{�'�D,���S����}�n�Շ{�ʒ��Eh�����>Y}Sg~d����aM ��l��^/���i ��\ĳN��!���<�9���
�q�
���;>�ۃv��F�φ�>=Oj�Kz���B<����D�3/����r��NlF����!V^�9
y̪�_+E����ޛ����s�6�4�D~��{)���A���o��0.�
b�_�+��#�3j+�M��vrR���ħ���8��@"��{�_�ֵ*�E�q��j�+��=��y��!"�VEY������WM��9%��&N����}�_�U�H�ڬ����DR�)ܲ���q��Z_�#��9��̓�
�i���d��A�W/Pj��O�HNA��^��O?|��Y��9���P��ևsѫV�y˗^�Q��a��S�Л'E$V��$ĵ٠K���l���V%����~�؆P�&���Sὖ�r��2�l�Ċ�_�V:?���A>Vs����r�Vxni��{�v�:��r4���n�x��H����3�>�y"�G���:�Ή�6��+}}[Q�vY�"���������)�3J��u4Z^�����E�8D��@�&��za���#a�V�Q��I2s[�1�yݴ�zc��Ұ�g�$��m������o�4bo��A��{<���:�4_�jH@Pq�6'7�_fK����=Ng,3�S4�i�a��,6�vs�ް��}"V>CU�Y���0q����O!ۇ�R�W����G[��n��#��_c?�w��@�_w���L�P���ۘ���=@\���'��L�O��?�7MY�i�W��I �c�틤^�����"*�37bp�6�9�ҜU�{V��YDV8?���£O��xT�(������%�s�%1j�<_�֜�(^�ܫ�+c����E�}�9�����d�Ln���j�/l���Ӌ�O�5�p���'�!M)/���2�k����Z������u�v���O�~�W|�%��8��8��|�?lq�k��"̲���B���U�?��X.�d�o����K�F��vd���K+��?���4�y{����6��A�p���p8�8 ���Q�DO�a�yAyA�?�=8u�V%/�م�<�i�ͤ�FO��*dI�v��{W/��y�Ouܛ�LF��p�b)�@՛��s�0���o� �XU��A��E�f��:KN5�?%O�����
1I�cs�߆c�L�����g�1H�,"PM��Ϭx4|~:���n!4{b.w�ͯs��|�n
���Zo��l�NWE��Ge�5 =/#p��I<M��B31��j����Z	A/��u�O4���$S�o�[S�!}�泹��cd |��Ţ������{���45;gf���Q�����2WV�kZ�����'����Fz�rؕ&�e�$��7��
q��LUzC�����-J�@>`b>�<W�����||Fө�K���{�/g�u�y���׾�6�G����v\vRNy����8�����׊�Ӆff7��cW1H 0R��1}��i<�����ê���A��yf��N_��mvP �Q*/�u��V��Ll�%>��g~3����١CB�_�ӳ�����d��"�M�o1�C?������妿�W,� ���޹E6�K���𠈏p��9��IrQ<�_5N�L���/�ƣo��o���!>�ۯR�l�ީ�s���f��ەw�甚 U���㙾��ɟ1B�y������!}ga�<����q����ȕ�a��$(h�s�\�3�]�1��<>ͭKU�� "G�����B4�& �.���:/�������%`�?��C�م�W�ٜ��㼔�uق=af�S�7�kiĒd�������+�P�y�٪G�Hgw��Tp4���C;6R���4����U����Pk�q]�`x�qו�
:4�X������愇\�q�bN)�q�v��[p�:'Gy���t�<�������Z�ZF���?�cd�]W_p(?9�5\6x� �l�iP��ooV����'(��!�Ajb��5�K�K˷�1�ey�5��jX��+4�岲=n{��������H�P��f���{9Q���r2� �ұ�uj�Y啜sٽ!���ED�m��\�r�^�����m�FX!E�&`,������ÿ���8@��O�.�5�ށ��ͻ(��ٮ�y�b�7��,�R�d;9]Q{�k����n��j�V5�_Q~�c��~�H�b��,�4�|��,iˮv'[g�.>��N?�9=�U��~���۪���ea�D�a��I`���;�h�7~�p�>�-1��@�01�Q�+��t���Q���R��%��l�/2��w7�ELy^(�=>Nn��n�3��~�k��"�)_%w��&���
��ioΚR#�p��w�;���º;��$iz��l�52?�Ԃ���54�W��d��5q��Qg�����Aa�Gj��w#�N>����&�?��SS��?��y�k��nYt�i�H|�)��~�G���c[5"��f�檻��%�͗�<�i����|����*�O��?RiŅ��1ET��!���*F��O��q	�M5�p���6��%,�qӞ��Z�0ߊ���(��c9�酃_�7�7�=n�ܢr#���h���j�8j��O��>K���>4T�V��E�׆��cڦ�a^š3��v�ӻ�»�%Q�;�	�� �z?�_,J���PB äҟ����|�Vإ�|�6I�z�����C�C1v�&�O�MA���c|��mz���Ž�)�l4�rz�u�C���i.������Dp���7|{�wZ�xg��j��y��J'.ɡy�o;Xk��Y�C��%�a�k�����8)�*!d�'�a���£!��m�6��ޒ�9�dAmspb��-N��xy������
J��]�ކ�����^�6�C���f�OԂ��9�̼��מ%���+���$�%���z���w��˄l��0�e�__�D%�<�|�X~��E-����'R�P�:-�sMx�cnwP���|!��o��2|>Ix�!Q@�{����g��&ΐ:(n�dAl8���~�������H~5J�{���|yXU���C+�^�[����e2�2{�����mP��3Ջ���<��=���DTC
G�C�:Um�+8�j��N�³�U@7�!�Q�ĝ7B\��/������f'�t%��k� �����LR�9
��; )������L�zTA��r��/H}�q\�k?���l�/�P�@�p�j�D����k��� N���;Fjq�#9��03(���ɗHP)\K���%���,��*�|��KB~כe��U�������U����K�b�Ε�S�?����?��7��g�Y!L0�s����,L��rϟ�9�Y`��~5�W!D��P��[oҥ*�u�����(=�EDEA�}�H+��/"0��VժZ'�Z$�f�9{�F٭�	I�'�(m��c%��!)E�&P���y4C���e�;n��0o�^�p��S�I���n�F��T4#��3�/��ehP�AMoq+R�V�ȻɈ��4�	�,4��(C�v����\�zo�=1���P��
��pu��]}��Ļԇ�3�����k�aB�� ����2"��8T
���$�Q�Ĉ��e�,	��t��w��9Q�oðN���,�2���pO��M�>��ۉ�k*k�F��[��WZi��KWGuo)�+�͚M�yI�'">��Q��O]�NǪ�+r���R ) �T��@
k�`2"�AA��~=O�G3� f���c����t^�x݊�kf�IyjɹW���ҵ,�)����cb ���A�F$�F������1�]d�ag�C�Gy4�
��zƟ�x��&��y�6����`��>���j�o�@���q���iҐcN���j]��X�׊ؿ'�l�i	>�h"6�g�^NE��    =E.�bפ:R)��~[�g�|�Lb��.�hc:��Y8E �+<m�ĕ���Z����sz�nẠ
�\/�Q���QY��`���ak&�-�6Y���wxd�������I��'}�w(��9���֦���Z���rc �C�*�fd���)���k%
~� ���]�,�?.��t�Kk�R�ά��6�T����W��PMI&��IدY?�bh��!>�U
���/7Ѷ/���}���KB��q�J���u���=�w�m�ʨ�k�G�\�
�b~u�ƂJ0���kE�?��l���u�Rp� 7;qT��&:ƧT�_��n���������وzһ=z�KD$��-�t_+r~`Cì\�*��,�}��~��Ū����'��2�1��D���. �d�_���[5� �a�� �mCWo�p����~��"US��f`ᢨJgC�\3�j ]���V�!����2p���������@�j� �ണ�7<@k�͉ne���P��r��Fx�W�K�DL�j�m�I>���v�n!�AQ�aI=:v2rd�Um��6�Jޞ�����E���	2d��s�@��	�U�Pw9XS�œ��9���X?�SyJ>�[��(�l0^kf|�GT	2��K�i�i�OF���R�ޠ&��Gk�P�~���Z�&{�4���Ia2���O�7m#��e�Z�S�03Ջ]5zP��N�E��%
ɽ�"ذ3�@,c���;Z�j����e^�g'�.�axZ�M�)#}~�����I3�1,H=��;���Q��7�`��Q�`��u���s���sk�V{�s��{WV��ʃ���g殘�%�rr\N.�\��Q�캐���V����@��h�׵qT���%	��Y��:/���\�_S !���]���J�gl�ʅ�TFeM�M�?pS�F�O�t߭��͌f��/���_�:��+��hKږ���5(�7���/�qQޟ��������)� ��֜Ȉ���ʦ�|�q� �����zz�~��*1�8վ�׾���ܥ��O��8�4�e{?%�}�2 Gt�HDΒ�t�Q����
6���uI�c��9�ms+e 5�O祯��)��{Y�n|b�<?�}3�$"rD�$�;�tX?
~��P�N��]PE6�V�=(4+NV��e8e{=�G�	�p�n/{fG*�ǜy������W�������Ef/���gG��LT ���?����m��H�g�t`n��м���O϶~�������%*ͥ��ӝ�<X
TfP��?B�ہKT�DpΦ-'�Z����"j�?Mm+�"y�n��*u��x�Ǫ�7=�u)y�ؾM�KC;z�Z�9�\,K/n.�כ�Xt�׊޿�5H�a���߭�+`̋ft��|��9V���/�Ƙ��I�#cy��Q��]���3H`�e��'ˀՄ�4�x���kE�_��� 9��f�Ai^�=�F,5��bPV�$ZZ^茸��F��l7��=R���1l�e����������_� ���B4�;��ݻ���<�fh��P�t�|�YxŁ8�����^�]��K��g�����`M�?�5�8z����	�� ���-���J?���v睔O�<��x���P�1�5D7!����җ��wT�(G��KAk�ؼ�F�0�MP&F3�������.N��<�|�؝��|P�k6nz;�]�8����>��4���ʭP2��Z�=�Fݏ_E^���w���_�@��c��3�l���]���hJu�>��'�sBa8�ĩ,��/l)�.(��vl�WAbI�+ē���Aۉ��:z%��]:��X��Y�٦�b�ߞ��p�W��CH��!����pF���ت|(�z:��V������* ;�.9Cdo��6[�~GU����C��>�0f�'G���U�1f��j�#ȅ�bG�ϵ���P�)���ˬh�%R����ݨi�h3X���Pܓ*��YVt�R�B�����b��+���K�.�5I?%Gv�I�'�+CD"�3��c��m}G9��$V�,l�zv3n���\mM�zS�������7m�ֲ�:%ɾ^�;;pF�8�"&P��[� �y��=n	g��x���=D"���E�U��.0�����\�\�D�m�����㋌g�|ǀ��B�_�#�7L�k�c�oEfp��-+�����LZ���Iy���{�v�Ɣ�M����;��$ϓ�%A���Ѳ`����A�;��LW`�vL��v	bOY���E����ִ���D82����R�k>�&MO����!�.�h���t�Ks�`��4���Ϙ�I�16,���3��h]?��������a<O��`C	�s�ze�7�@P������"���Ch0�R��L=w ;l�_;�@!��	�v&��
�}�3�`��`G�s �J͗�%E�j5΃�bnqR�-a>.�I���ܑ�c�r@~�8F��N�a��!~ט�)�;��QO���|~�d+z�"HMF�m�'MaRZ�L����z'�)~��v;ب ��}3���2�SNF���z̎!*�xF"����L��������6�AØ��>�p���6ģj�6�x7t�xbaX-� �[�&,��k��(�f0��-4� ^׸�L�[)�.��H�-�4�(m��k��륒/M��Z�>�Ǖ/����b[��A� �yůs?P,�ABt_&���u���@���ݞ[A�J��e�>������l}�i����V��<�Р�,b��
'yvY�5��bF����xF�;s�/��,��o2�(����z���=�P��꬙m�r���]�6��O	r�5���'#��m�M;ě��,DG7�ds�))+bqq����	={4��ܧBo��vOr���}�<�W���p�|a�OFl��@��s<�	��&�d-�U�Fm���;�ց����b�g�ێo�\m{k�,
�<"�cҗ��l0Xb�u��6u����Fh���iNwBv�5{ߨ]�a��}+p�Z���"I�q큂���Ɖv�s��p�[��CShp�ݟdC��1]hEMy�%�>W�x�t>$�./G�g���>q�n�\�w����qZ��um�tn���4F	��Eج�T�����������xF����C��+)pakyk��s��Y,��t��`�=-�6���@�ήV�9"�w��@��8�K����=���v�XU�H��z�y���h&�ܤW!+-��_���W�vX=F6�����Tw^E�/6�ִ�����F�q@k|G���/8��Q��4�ᄐ�b�^�Qs���C���0�^M\cO<�fd��m�]*�=��x�S�+![y���8�Krf� 6�a3/�j��n������ ���Y��F�)o��>���b�vŲ��$r�9�`,���adQ5�G?e�����U(�	J~�y�Q�?��5d㹹v��J�����2�Эn��c;uw�s�<��#ء�H��E��%���6N�5�;,�F����ŽmO�>��v�=����p��M$T9�T%�����l����#Ԍ-��6Rx<���'?i�N�'Q	$��5� �3��x��b4�P��@����ޕ{m����>Dj'��<��a���.�xB�p�:��x�'#N�C�,��,HPfD�%sѝ՝2�2rW�Ҩ`��.�+Y����q���x��c�k�0��:�5��!�[LF���ViM��B=��K4ϸA�
�//�0�������3N�qО,#�9��(3��'�_;�^No�[l!�p��]x.\�aQA��|���#tcw�7C�!M!"E�j�s4+6�o����μa�^K��_q5]�+�M��ֳs�~���'�0<�*�d�.("fwn�i��S�Z�w���*j{��*���S��,;�������&��^_�V���b��L+�q�c���8>Tr�G >����5���xMtq],�w�w�����)��w~�����"t=|q��~�x��,��k�o';��'�oJ`A��x��v����8��Q����%�|[�-R����@vuVn+��1(�Ѕ	t�c2	͓��I    q�˾�Y~���~p�l7|��'�X�uc�3ש[0���a����zV�8� �/'W�A~���I�:��V2i�ʃ��l�r>�J�4�&�l|��r�R�?d��p-D��̸U-T����< '���8�ָ��%.���<P�3f@y�~���G��)�fʙ�w؄ISܷ��>������6�U'�C��H�cHA~r�E��踝�Ěȏ@���󝑲�"`e<�g�:^j��#���^{�����}y�Hmf0�{w���$Ued�4{`3�p�2�6p_�ζ����Ut3蝪 �q�����y3$��?rt~h�ӥ5�}�N�[�m+ŭM�TIw_%���ӷ��
�:�7�]�Z����@�5mYς_�$k�.��ҧ�!�w�x0�+��w�
l�$X��r�ڸ�C���{'7�r�������E��5���[����g���"�d{8��1Hi�g[h��1K�Sp��=���bT���}��9��<����x	� B�%~�n����NwguH�"�1�W���;�3<&�����AxS�"_)}����-���Z��gˎBo�X�T��B	�U�P��[���(śe��)��d
V���¬��x�ư�w�Nh��<jl����B������l1B��,����o׈�t��-�HV?�����?Ғ��u $�ta�=����uq��}����������׫��D�,A ���U�*��P�ڦ����Q2t�L;�	ɟY���S�:�̶2��bX���6��jE�<>�݉i���m1f���� ��YUႄ�	�W�E����כ��aO�NA�g��	߸*��M	Y�R��s9�/�'!��5�de	j!!����7���gB��f��ID�>$3^�s���9�F���_^n�{�(U� �)��
�~ɑ���Ұ��/�P�h�J;�����{.Rw��Ɍl7Taz�^H��C�j�K6\@j��`\������V~8O������G����(�3<l��_3��C��B��M���U�_Jf���\��ŵ�}"<ݼ�b�xy��^m(�՞{�0�HuZD�2��\���0h���s:�ƺNf��T]��������������5����=�k���5��9��C�2XU�x`#A�1xF��5a�er�A���!�����ȗtD�9��/b�7s`Y�y�e0�0CO\����1�T|����,+�r��H8(}XMb�{��&3F��I�nV/��h���ӹ��u��uSZLG������8�к�F��%{u=��Y�$��SYQP���i����(-�d�	V��C��*|8{F*�{��g�U�����$�U<}n��]Y�UA$p>E�v$8�J��[~���������/0�a�w��Y#>�C�e���D'ܷ�|�;�=>V�P��	��Q���!�v�Sbf�A,�gr.Z������:���"'�A�A�֏癵k_��糢Zc5]a��:�����:7���]�=�"I��@�~�m��o/�`2��%�F��_:��3�T?7_��
���8Nm�5su��ާ�~
olO(b�����))EI��t�<�� �.[�����H�P��alX�\&3^�98�K�i�"�նlt|c��3ɇ���G�fN|9R�s/lIJ��=�.HC�`!���j����x?�+(���|�E�(���Ɍ<6�ĸk�d�ı,ɺ��j�n��8D��vԧ���A�6�=��
��$�u��ca�Q����tu1�Y�����O�A�;}Ήo\4�O��N�n��75;/O��,�+ߋ���u�=�H�?��Pށ�Pj,����sjd��tԚ���O��pQOK��Z:$��[��K#J�L�Zd�|���MF㸾��&�g,w�k�u��z��L��B�ؔ�̸��ca'��9<ˊO��꒵{�]��l���Ȟ�#�%�Yd��#G��DB�����؅�/�ʺ	~�$�����v���j�$3�����g���bI��G���%>�Koh��E�k��h�B�",��;8\u�ZâK�V��
5n��h2c.[Z�hK�C����ʻ��lW�9X�R{���-��x��k�;���hQ8MD�d��!;YI�[ a��IlO!Q�$3����٩\:��>�&(�ֺ���?F�(���q_F�8v�����$�X�X����K�t2��'/��нmA��>���%;�:&z�5^N�е���Xw!ό��얩$%�Nf�3��~<�'H�� \0���pGկ�d?���;����lWɌ!��O�ם�zX��9&��3ϲ�àJ}��չ���z�t�UC	��<C�L�Q���If�_:+�~xJk�_��q��U-;�|^��,��P?jv�
IFF}M�ӇJg�U!	�Pq'#)~
+B�6�Y��hj�[�1B����튆K�ĺ*�ٳ5�D�Mᰪ�;�X�oNq�N
�t)���1�7��֤��A-Ԥޟ� O�%������3g}�q<tҡ������Qw_�ߔܢ��%��ۍr�Q�K��
���+X��d$�9��Q��DC�D����l��l:��K�j���5��6˵"8Il�o�~�U��r�`��h��U�֤���/@9wό#�w��V��+��[>Cn*L%9m�Z��"��úo��
���h´y���\�N�q:O��-����~���m�rt��+�C �F3?F^�	U��.�� GӮ�F�g�,�m%�/.UL�q�;����+Ƞ=,n|�q����>h��b�������x�7��~/>�����:-ǔ���D�x�1�cs�Xa�-w��x����01ʼ<r���v��<i\EAZG��$t ��1�q���Z7�R��
_T��ԗl�yr�P����QS�a8�<�	8���S膂z2���/D�(@C��#Pbɐ4Y��.^�L|���%S2]���<ŵsN4�����*	|�J�(��+����V��ۑi�����N`׌,2;�c��q������N���nj���~����j�l4���8q�ƃ�S���5��_05R�JfT�����Ąb+
w�#M�=��q�m+:��JU��?��=�c��P�KӋ=D�]8�A&�d	_+@�C-������f��V2cy�^����j\�B���I�ɬ�Mt����&샆=�kL'ϼ%��	i�04�|W�2(��H��H�?�'���2�e�P2�j2�*ay��e�|���m�Ш�����1+]�����M�LO��n����z��Ł��~�����u�±�t��~Y%�iI��5x����}�Xʌ��I��%A\��������\OOg��4��f���	y9\R������4����T���u3ᡍ��b���%[�-!V#���;f�Rs�o���9MU���0�NК�i��J��3�p��p6U����X�{JT��S�FLTW*N�p<���ZgW���/���I�Z�0|����R�?�˲<���00���i@i��g�w(�оg���>���r-�%�G�m5:��#epPt#~���(�]P��Rİm;�Q�f,͜�.o��l�dl��`��㞲цpX�\Q7��K^�e�ao�I�.����6��0��'#��n&�H4F�f,A=.i�eGG��m��UƷ'�ng�b�����w��a/���F08C.t˷�_^�8$^�����L�5ad�;��O:�	�ݬ.�a_J�:V�2?�������rgY����i�VR�z�P��{H��=�H(t�cu�XS]��|�z'�(�_n:�	��N�cJm��1����a��g�E�Q5�tg��rS�vEN�����9)Z/�v2R�N�g�Os��/�.�	�$�� ��d<�����r�����[s(��v��gI��v���0H����)٤����A�[��(��3ʐ/;�\����S���vD]��39ڡ�b���>���Ӓ[#����Q����ț�߳�z9`CFj�2�u�S� ڥ3���F߀��R�{�RI����a�x*-    �zIZ������Q���!��p_I
44\�
.��HA�A~0�>���TBY����RO+���\=Ү����-JT�v��A���Nx�	s�3u�R�q���v�h&eW�.])ׄ4�8~� �I}w?�{�$�U���½a��QuϮ�}��+�l�^X	ZS��)�3�Λt���o�z�d,Ѯ��]�;��:��G²R�qҒ��]I0om[�9�j��9�*�K���0 ��=���>����	c���N��F�1�q��b����瑊�yK�M�`�������d�ʡ^�guH�d2R����r
�=/��*k���ĸ=$4�)�pE��=�G7D��p�ݒ����^,�����Sr8�ڃ½�N�d��W�����?#
�~&){���!ܛ��fZI��:���2��m�d{���R�|�HU=��I�J�V֯��?%�ƺ"m���Q���B�?��]��򺮎Y��]�"#��"h9:���3+_��6}�,:0����7px>�Y��Fe���C:c
b�sdtI����D�����Z*cl�Y�*���OZnϥ~��ښ�,� �~��TVB�P��Zi���Ca�җ�	�|�3��YZ��{�+D�)�ѣTS4�i��M�b�ڊ�a�[I��yޙ"VfY(H�9��	3x�V�����glM�(	�Q(�fe4�z��w�p�cp#�2�|GAWx2��Ȭ�ո'd
%�,�0,D��xN�R���������BV�be0�B��v{��C�d�7U�X&�W�7��Z�W���i���ܹ�6_���
�X���Dm�	�*���f���� 	n�3��kC����������^�]���Ë��s��N�i����'�f܎�3q��=���#� ��#4����)mP��9Hg�A�>\�v-�X�nԳ|�U�λ;	�G\E�s�`�wnqsqw��/�s8�iP
ƃ7����/'#-���(TK�����������&>����P��������%֊U[vr.��EH-�-�kg���N�������i�`R��d�C:c�AI���{|���IF�nEOW�"摸���])��K�����a�D|�B�x�y���	�fp�4�Q�l���/CV��x�V��mV�x+	���L�yG��	_Ֆa�U�tkdZ^u�V�ZpI��C3�C��H�?hBo��� �>����b��>����o5��K�\��NW��Y����OwE/�(����(^��3H��d�9{(Ѐ�V� =�[�����H�<����v9<�+O~���am�Lp� �ꬱ�����=�lʜ(Y��J��F`s�-��C�,��h=���J�'Gڟ�������&_�ՇU_^���Z��a�:� �uqS�I+m �`ד���v|�{����?�	�28�oN�V�^%y��z��)������F����3v;)K�A>��`gT�O�6���J����.�ZX:bE��^�vY��&�:x�qY��jIU\Y�US�CR�e�;B99r�%��N�, 3�d���
NAU��'�*���6	V�ܻ�I����^�oUvK]<OO��QId�јf�X~j\Xإ��P�N��i�������9L;g8���N�^��{#���f�'�����?/�.�K[l���/��l�
	�V{ �6m=9�����/���Pl�"8��K���q���I7�Ճ��^�]�m^Z��~��=���.�<mەh�p�~�A!3�IKga�~IG(O���}p� ��FHY�T#v,6]ą���j7i�n[�^b+}�7����ͳZ]T�AO0OB:��B��7�m�l��U*i@������V��Ν.M�2�<�������z��{:�'�/�p���0]$H!l�*� w T&DV��
���M�lF���>�?�9���a{�|dM��3Ѣ����xg3>�r�)��88��I���i(��d���`VN��-�f4��ɏ�X{q��d_'GP,�l�8vʱ���D�*y#?�a-<�.,�(����A�Z� }`V�5#�#`�vԪj�l�2b��F��_m�*G
;�oU��GΖRj���qL��ٯ��Xn��f�[�m���{p`�fZ3
�Zn�_;���-3�p2�l�_m֪��'8��G�C4;>�Ż���e���f��v�}�]MT~�����~�.D����7�<�n�&,^6�ј	���C�~w�9h��Vͧ�ɂ�}9�O+[���xC8�k�$m�<��%��e�[�����cՓd�[@�����~���a/>���V�0W(�.m-f�xL��Fu��C]?�gay�Z`�ǭV�_�K�6a~f��m�.ԁp�Ux�wǐ|lS./�R|Z=�*��������9A�5i��M4p+d�p�ug�>x�E��
Nr���G~3�Q�k�2�/���{���Z�U�o�)�f/y�BeKj�55�qU˸�e�͝��ӕA4@�I������������ry	�v�GX݋K�����+��R�?���W�gڱ��8�Խ�y�i����tI�UgP��^��ot22�'�o���SP��O�!\O�Aq��~zk����[��梼�A�����|�{�\�:]�Z^�Y5lf�ܢ�����v��f��f<A^a��`������_�UG����� 9s=S��u��Y�������\�`�F�bX�ᯙ9	��Q3:V����wpa8�mbq�ּ�xOu�Q*����8#-����s������(pC�<�ŵ(YS���?���m]�e3��hnm=�+k��\�{K�do��d��h�Э�t��Z���)J�[o�Y�"��B���0 K���6���`����b=4��y���Smlo`���kTg������^6+�j�A�E��)R?��]u��28��Y�Fi��<��f��t���ˇ�d8��t��U�f��1�Ѯ��lWI#8��DX	J=��4R���� >T�d|�,��l�
!�ff3ʐH�V<*��ZB���?n�ڝ�Q)�3W��=���3�eE&��7D9|��E��F��Oh�E�Bj�,�{%\;��:h3���1�;�n��vU�$���.µ���V#?�=��c���Z�uD9����Z��P����<9x��E�v���ft���y�~�K�/Y��{q'{��k뗰��U��ƖC2X˵�� I-r8�Z����	|������+���b����Z��M�Dx����'=R�ɷ5�F�����8)*��s9]n.���K�BY�!�9C�%Kfw�/��.���fPa�n,����[�p�o�feԹ9��o1sy
�rS��p�0�)��r�C
�M|����dA�G���je3�pR��] o
�����ؽ��U=�&hk��(��E����5y?b�� �F�搏�u�j��vhe��=Z1�$x�3��6�)rލ�<A�h.��a�c���~}��~t�6Pw\j�r�f�j8�p|��V��'U���&���ؓ�������ʾ�:�|#Y�;�>���8.;��nf�s�3������y�����>����\������!G�"�q���C�(���[1�����F~K��m_�W�)L~��`w�K�ql��C���sPV���N;Pd��3������N���)�J�?�
�V�v�R����$�n�f���=ðQUn$_^�4ە��Е���YH^#%�dd��=����f3��"沢��,Λ���6���"s���}�ly�	��xgd,�I�%���G��J�9F�xbAVV�=�&��,,7���BnTc�P�1h�b���U����N������v���e�ۡ�M��'�ՇM��	�Z鯙�?q����Xِ�Ȃ�(�K3�U��t�a�{�{�����90o/�lV��M����/��C��!�R5��Z�@�ۙ����2kD�$0��JT`���{�=sI3*��r�Y�9�=[�L]k�l�8p[)���G̑J��E;���C:s�W;�����f��{e'���Y6S%�d�U�BT�W�]    ��F�h��4��u�^,�mx���'��	j���4P�Pq��&#��Wϡ�z<��`�5)-#����}���2p�U9�Z
��Ǹ���+����&����!A/`��eP�����������g�R0������c^�{����Ym�^�;�R��2��&=��p�^����ie~A@�%/���^ �њ}~`>�E������t��Cob=�����督m����j
R���=�{k�+��n4�9�j	v��	6ؾ���
�Ȫ�Ȇ� o��H��MQ$����<�t8k���b�&��rV�25خ��~v�-�淾8�91��b��ф}��	^4���������<�f��(�I�~��(oN����$b�M���C �\Pq�o���%eZ$O��/T��:ĺ[�����"�Z��g@}�Q��	-d��5�W����ө�L���*���Q3{�3�4dM˾²�no��E��� km�E� �@r$���s�'�܉Ç�=���r`ǗU��zu�[y��W���L�z�,o��kZ<�)u��ykF@Ϙ��!���G�u��^$��ʉ;m��ե����s0��e�#7�إRY����͋%�vdN��X9kϣW�֟�e�i�u@)!I,$aw�b�8?�5s�T��w��<���AX����.�<_7�8��VȞ�k�-�-��L�Y�vs�m����$	�?�Ҝ�qP(�Zu���S0�N�U�z!���zh�>��ϓ^�����\�����a��R�W�r��`[��Wn�mEBEXD���@x�U	���)ܼHR���(�`=e�-�e>��.�|)�㆐L�qE�S{/�ӱ�}���$cN�p����B�Ćǖ�[Xc��d���tL�C:���x�V��������E2J��6Y�ڶ	��vL����8v�~�;��K�N�U�,ְs5`Fj�;�K�Nsp����	t��� ��A���E�m����!����|�w孍b�nq��82:_y��1^�������A�24��5��s�tz�u��:W�ļb���7SQ���K��cR�h���ϣY���S�ݷ���Rn!�QSY�|*�L�'#�ҿ�O|�0��v��.��L�XU��u�(���[��.�J�z���'E��V�`z�<R��aˈ��6��ȭ�:��kj���0����:X[|�黵��\���R{�q�j�0���*��x����7����%�BD�!D����?m�=�p~	�f��f�W�:1�6�҇��9���T�����k�g�N̅����{T�P8�*A,����		j�3w~�����K�|d��L�.��Xx(*��V����K�s�xۧ;�T�f7I�;���е�/����م�������������5�3�j/��n;�p�T�вJ����g7C�8~������(�f�ty�kBfX�H����v�E�z�r�'���<Db���	����+E�˃X:�x����򖇬�2�1$�W>���Ho�b���w�d�����C�XO���b�ڞ�܇��V�忬�2Z� k���m��<c��3v��Q���}����e��Q�A��
\������̃���r��J�|�H�̦٘�F�ǧ���޲B���#�G�����KR��!�+��b�����@Q�E\OF�D
u�Ț ��ky�O�����}�����?�}���K)<SV����y^m�LS���C�j�Q��rȹ��]����(�"��|F�+B����ߺU?N/��c���<o�:"���Z}�^�'M�k4��Q0���'+��⠢���х�"6j��@
"�d���4	����k���og~�w/�1�l���$$(5�a7�������-ۥE%Ԓ��̓����EI[��)yn`�h�.��*����꫒'
X�+Zq&�}�v�������1A!Nb(�W��䮀\|Q��#���~M	�!ی-$�طVr�M����]�]���; 8�F��B����������6��<8�^��,�y��:�$w]}��Z���F�?Q�7�K ��p�����7�^��N�g?l.����o��GP���
$��h��8*Y������C-�`%�]��3�3̰���X�q���g�T.�=��8^�x n�E�"�YɄ8�$J*a�U�:h͋?����T'M>O���*��ܙr��|�<��n򻉸�~�V���l����R��i
x�ԃ�&lˇ�	y����h:�h _�6��(8X_I8|�Pk��X1S ]��x��$k'{qXގ>%�^�FX���&l�E��+���k���s@=��߬�[t�:��{`���&.�Odn6M�lwf�5��v]JC�ĳ�{hI���4]�Yl�i��ogp+��!#�fi �m���gh��GJ�dŢ�!�z�����$5��kO����v�uVq�Xs���%G��ǀ�$�E�2�������V��+�y��kK�O���:��eo�	��9tn���fvq���L57���i\���5���qf;o~�w�ŷg���gdA8q���n���WŞ�[�2o�1�d��{f|�KYH>]݌�| H�f�Gl�V������s�Y�V�����$;�}��c�W˹��>��'Ie%mlؽ�>�.��Y�@��A>�fy����K�Z�T"+�P��a����$NA�=�	�X�c��Y��Y�?F�z!0��G�AW�^��0�������*l�B7�@��ʁ[�� �T�5��mYx@���|2o�C����՘��������|>X�s%�Y�1��Dˇ��>���.�[�@��� �����z2�Ϗ�_��Ҫ�g��ΝA�:i���2+ng~O�}mnHN���.4J�te�ٙ�$�X��a9�\
w@�����|��ִ��$�]*ߋU�=q-n\�$�?���`S������[�8x�^Kw��n��y�Bj!Ê��%�$����/�X+T^ȣ������^vW�Aj`�{�qB�g|�"�XS����]�0kvH�k+Ӊ�8A�,c{2�Տg�!q�~A* ��$��:Ϋ�3[3^»lQ���R�U��uG�Of�ע�o�1s(�S�
�Y�z�c5!�< g�-p&������u�����q�
e/�3�������j��Ό�V�5�ń��ђ��H�Qh_K���AFj��UlW�������.4�X�f�`}��'�9��5�C<�-�����
Mh��r���y�&�<�o�1��yF	ܛ�x���Z��HG�w	���K�#Q�Q��ru�g�	e	{�a�61�� k��-�'�� �>�i�N���� �-���ZP?$��g*��0�,fd��8Q(��o���jy�tPoJ���튠�u�׼}o?��:�(MGt�HSa��݆P��	f�����@tK�*Ў&��7E�Qw�M�=6vF\����7zA�])L�������,����r�����"v�V���QMt�H8?��M1��V���z�uG��kt��^^$��$!@'���3�{����ڛ���';cW���D�Z�"�\}�z���5�м��I����2��PԷ
�-,֔W��)�24�8��2VC��f_#��U����y��d���.uZ/ߦ*NV���Q��2Y�������N��o^đ��.���T�&@Bޭ�'����Ϝ��U��f��y��7�g�C
ae�{U���k2��HQ<���>�9�U�����j�O�8�2����ʜЁx�v.�S���?gu�g(��g��O6aas��c�w;Yt����%}ذv�^��)�+7*2E�Л��/nms�Bknnk�:]`}�N����@��ϤAt5O�n��i��F/q�ĺ�#/��L족��tTϗ�F������o$A5�s�݂��KnmA�4�����}>S��}3������I
�֛�Ҟ0���ކer�A$V<��T�Kw;Ո���ʈ�����4���Gi�������3� @M]>v�c��UF$-�����Q���:^=|�o��<�gH����o���x�
    �V���Fk�̇�`/����g�`���]El(<����i�1��ȁUf9���}�����ta\Kǧ���8UP_	�#�-�&\r�����f�<t��35�����K�����Aa�������	ߌ���qP ��^㓭������G�@*,�`�x�_+��C�~C�U� 	��TA�<N��\}F�J�Sy̼�W�X��ʨ«7�w�N��3t�5T�+8�'tЌ;��5s��v�=�vj��[7��&�L�H��p$��t��<y���O��D��	[�h��zؕ��Q��Ǵ��#n��u�ȋ+����u�m���A-�|��P:��.�3F��~
��(���G�XQ9�����K���#���U� ��xV��.jb�% �fnA�i�?�P+�� �o�O���{������J��
6���:X��3�N�\QX�K���7��[R�PE�#�A�r;��џ[�O���!��3�P�+�|�-��-K�v�iE��c����L��y�t�I3�2���Se6�.�]� ��,��K�9��4�-����U ^�D>s\O�s��P,a�����	'�V��
���X4���@��M%~�iZ/�.y��/�P ?\���L.�'F���[�d���פ�a��G���9�7�]UZM��)�J��Tbh�/k�	��L��y�W�TS�jX�gra�|��%�.��d��8�ަ!�j��y՟$*xމ�^�ܻ���@����=6P��K��KP �$��M�io̷U��\�uaB�.��kۿ��-�����l�*���c��M��=6�}7Q"���k�o}7��&�8 ցý����J�M����N �������*�u�r��|��E���dɆ��Bb�@V���� �?e����5�)�ҥ'q�&�u�|	�y��b��6�0��5u���2��컈t}�QS��5�� >�3�xy��>+iS��׼ݓۆ��Jy�>&9�-[��.�j���
^l�����n����q�r]�5�0bs�Y�i�؞��ڶ�,�+`�Q���L(�'��_b��i離����wW�ˤ���5�#:�OO�����R�}>��,�͏�����?��~j�W݂m6�桺J7�F���ڗ���\Y�{��.��i�z%?��?�U�x��%rJ,��G���w�i���y��A�n��|S�V���O���-��z���{[�2�hG�rJ�]�C3�}ü��������ao������=��F��=L�5H��3��'�[fm`�*۝]�-�T����&�K�a�%��V�QL�,]������`��TeCV�m��`�ģ3[؃7�
�սӏKp�������nBܭ��i����Y'7��-�?���%�X���
�D{��
����y+���~_��b����w���?���yY<jby=��w��L.w��0a应آժf�%XV��{r��֧���ȇ-�k��m�ge��&���k�~zF�az���vs`ŧ�&�JJ�$��G��n�=޽��)��p��,�N���ܳۇu��"���쥁�Y��Bah6w��}Mn����.Y*���)MbO	?\�"+}�/��q��zC�ZA9�!�����IњOF�v��y>3�%�ݍt1�K��68�K�FKvWG��M������1���n��k�f��]��g����yU���ҁ(桿���Ƹ��}z�%���{��
s�X�-��D��w�X��y�	�Y�k��ˮ�>����PQ ��3�g�Xo��|�v��V1�j{5F��ł��od��p��>{�ń䖯{��Vǧ����LJd�4�P�|�vkX��-���*�?e~��S�)C�xn�P~\�{�$]��^oܩpL��#P��V�z�^�k /W߭Kt5��BҘO`�4]�Ķ;���*�Ų��Y�l�P޴�V̙JȈ��ulÇ'�w0�2�ݬ��I%kgO�^�>U�o�W�.N A�Pv��fAnkx�d"`� �
�B1���p�ðġ�����u���j�_0�q����F���6��Lta
��� �R�r�Fk���l��:����bf$^\���O��x6&�oʧ���{w��s�����ZM��pMq�=]���TaG�V��H�������G��T-U�l�������f��w^����~���qB�d�QN�f�o�d�gȰ^�h��BGe(x�a "�\��,��?����P�v�� -3���D�j�R�K�[5��}Ki��F��.�D7�4^%��~���� �.v�B��l�N<`'�
f�!�C�T�[�|�t��8&A1���]��ν�p5��9�q���]~LN����Z v�e�ƅd�L�s��tv[$f$�ݸ U)f���#�%�K�oGٽ0�����xm@����"ü`�q�?�m��z!�^��I6��6�����(����Uh���k:���rJ8.��&�67c�^ǆ���+WN��o�#N�����-�2�� ba!K�����vf}7-�ЀϷ����pn״a��~�U	�jo��'B���%�~��L�d����^�@�j|_��
m#��"����\,�UaG_�\��{�!�������S΢,1�
5a��+ۧObM��!*,�s
�(��q���Ҫ�ՠ�p��hG���Ұ	��Z��a�{�>���Y`.�e) �iN����ƫ��yf����#�&7��͂��: ���­�R������G��<ԾE��C�Rs���3��;���y�\w�I������a�ݴ�♀�f.D�o��ևK�mk�H�E'�H��]��sW���D�]�ajy��h_؛�}xJ���b�VC�����:�5a�?2
��E| ���ό�E���ݐ��=Ŕ��7��(����gO� [5���'��5�f\q<H{�<tx��}�"�;q��/;�Si`��EP�?=�A���ߢ'�g���c���l}�;����B�uР�{KT���_�!�~�P�C�V�4��������g����p_ܛtt�-�ȊXJ�1�!�].��0"ǀ�{�q�E��`�5�h�f ����b�i3�p��S��xDq��!��)2��/���N��w�ǁ0���w�Q��Z(p�$��q�5��Ϧ�v�4L9�+�tVU=���Ń�N�9��y����캝ڥ�0�LL,z�8T� �x�0�SM���_�H�Ie��f|�3{���?��A~�Cʝ�w{�7�G�xMM��ޜxs����S�,%$8�k����7벒���	�W��r�4��hc��|����]�t�Ӏm�`<,�#o+�t���������xTh��$j�?�i2�zS���`��H��Ǔ�q[��k� ��~}�t͟�؍s��}��/'����Ӳ�h�*/�C�A��>,�?l�k�oa�
��.Lº�CD��o(~J6W��;�.K�} cq>�Y[$BR��{(QƩ�~D.�r0�"��FQ�5�<�[�of�@�O"���'��_�u�
�a�>����4������>��:!e�#D�lt5v�ZݫT� ?�r�"p
�u37@�Lw�#5���CJ]��]+��ޛ�5`�#~�YO��ܪ�D���E1?��[n�͑t�]T_�X�_8PԮ33�)�ϕ�;\KMYy4�.�R���j7��S���y��:��[�Lqv!�PZcqG�h�f���.��� ��Є-+ş����^,>�,*;�$Yڔ���QD����ۗ��c��՛X�+�!� �Ah����k��,`Q�W�80+��3P����`�X�YAt:7���[��;C�������a�ׇ@_V��Sl�]��(�����6įH�]�B��/%OR�3�%����]ֻL�d�Q./ù0���̖�X��{�5)�d	�(Z
)�_9Kث1�Vt[���<e��@xn1��s�%_o(F��ň&w���/GKڷ���Ύ`[r�=��ӫF�u	���8 ��'Z��O�F
��:/�Ln �'%Vk7�1�^^�S�����u9鳤�F    xg�����Y�!D@�?])�<˃d9��(������kӎ�b���*���ܯ����P?	et��Cnv���>�m�A4���>`'����H��f8��F?v1���y-ز �`a Q�Z�L
d|'{^W��΋/G�vݝ3��ݎo�3�6}��5oj�L�-�\����"#���}(��I�r�?���b��f�Cu�x��|_�����̶��Ɠ4��������{�>�a�9,w��K��[�i���rX��c���|&iނ����*ǜ�㓝n�C�lt��/)?�-��J���M��	w+�[�b��	�G�����Sw�Y���b�˙+���ݚ��ΩXJ�v�wWu����s7dD��!�
�؉����xLoC�0ьǅ�|��:a���T�]��]|Vτ���)uǜl ��}ndi����]Ԗ}ܖ��Mݺ�K��+�N���^KI���o�u�40ʙ.PU�#�o:O^�G�b�O�yG�%���Ƹ����Dx�'�	�g��&����mF�2���$�6�K�c����*g��:^��O���E�:���=~WW��6N�+ֿjY���%�[�Q1�$9`Va�H�A��r�7/ 	<K�7Y�3[`�D��AdW0�;V%����(��4[!x�}~�(ڼL$@�+�Ib!��].o8�ݳ�FIm]���zXh�g�3Q�a}5���~�~>�讝I��%������sG?�u�^P������n>5���C����K�ц����r�
:�qq�*�{���[ڬ]z��a��P_�j1��iXkmH%���ᓛ�͙\\��M�g�-���,�m��s��U9<8*gڠZ�w��'S�O��o���V+],Wa-� ��c�yC���muֿx��� ���4P�$	?V�nG�Π���3-˄�{�MDX�yRZ�\��xJ��? �m�=~�����yOs����p�ŭ�͠�&�Y��4˅5�l��c����H�?���
Yb|pz���:b��/�}'�}9��}f��]�.��.$��x�/�_H��@�Y3��K 2ɽ}I�MN˙D�G�Xm��g�M��>���U�moF1�ݳv,as�]�)g	DZ��[�	�~袵���ͤcYޕ3}���v���c��>��>j��pq���'O�,��F�z�l���f�T�� � ����Z:�����m�)g�`�'�2�ӗ��񾉔�s#���u�|��45K?�O�r��{y�y��6k(�,$��ZX)T��]B�t�f���A���S΄A/j��N��.�~0�&O�F��� �4�~�-iS�pS�����q(2#(0��Yz[/f�+s����*�5P�8,�!�H$sH�(�a����W�j�RE^6�=��l`=P�&s ��4.��u��ɾ�IV�PY��ܞ��E���y�ni��K}Qܙ��fW���Z�e����f$��T���dq� [/��e {
 ��u����!X^����#G� cY���ϴ�!ir��v�]Ե�ۯ�&�L�n�5�H�@��(�K��#7I����)gz��?	�N�1��-;ӯ��.���0�&��n5�ߎQ�is��7�ÃC�$[4�,ؗ�~�YF��o���`9f�3?�<kl�H�`�,3	��#��n�*�bV���Z�owݧц~����G�aC/v&☤���L�R����r�<3˙8�g=�j�O�i{ɰ�+��E��Z���;|�⭯I�}��@)����[( IG����2���k����@� U�˙�M��9��@h�16��{z�'���e]��>�sqǩ�)/ˋ�4���չ b� �.*�e��I�M`&]vR9�C��1N�rp��_�r��x�S���N���2SK���P�9�f�#�V�A$O�Y�͏Uf��f��u�9py3_��#%u�&�&��T�3�.fٌɿ�k���y�8W���+
�=�_�*�CU���b ��&��fM9�sy������۩�\=�;R*��aU��c��z|��N9��J)��@�KEگ>Ң����;��;tn1�3� �|J�j��J;��j��	�g^`#o�P�o�}�l4G�� �~tY�M���՟I�c��R��O�-g2!\W�����9�Ao�L��˵�G�T��a���Z#�J.vE��]	T��F��5�+�B�,g&��(��&j4�|�{%`n�8nZ���s8�q�����5z�����т�Ij����q��.�mA˟�zM.��0c��9�
w�S��=Y���N��>>�g[��?2�>P�!xՏ��%)��`)4�
o�U;���5�揄I�UfmÇ<S
���n���z͓�!�]�dݺڸ矉A�;��;��#�au���N�Y��R�����6�y�m��e�W=$.k�bΜ��W$�9|���
O��Efi`��v�������&V;Fz��|�%p��@��8`%	TÂD4<�+����w`ǐ�)gbAg�kj9'�m7G�%�إ��n%�\��z)��U�Zp��'���{fQ���عP) K�F�C5¦�,wQ���r&p���S��u�@h�r�ݛ~�؊�����Mh�!D��F��^-Np�x�1�ZC.尵-�m�[?r�Y��U��3����H��@���\.� ڠh,R��b*�P�*��!�I%��(hا�����U?���*�3��BZ���+��=�W#~����<j���2��H%n7�ogM��ո,��i�iX�R�jXUV�X��ZB��+�S���l����I�)kI�|�W
Y&[����L3���O��!gJvb7��A�D��P��sแ����Z����H��%��ea��Fxʟd�L��~6=��F18� &BQM�<�.�t1�c"������,�/H{���(���|8�]�A� %ir���� {Ԛ<�	S3��h5?�U��^�o�Q�*����NBl���&�?�N��,$�'�9��b��ZX\�̄np��m�X�5p�1��u�����<�y���(x�SN� ��j)��M07vZ��9
M;$G�G����d�j�� �/����-�x��u�P(0o��I��iI�����>���mֽX�T�t�����1C��3O��x~�{k4l��:�1�Iz����Q��� ��f0��?U�q<�9��-w�w�4Q{��3�7%�|v���i����ƍU4�(���V�oA[5������]�>lXZU�D�.~x2,(%A��W�Yq�8���mrC�$Q��\NqӘ{%\?�=�4T{_C^��a.|a����(lM����3���������<_J�t6��H-���'��?�m?�`u ���">���tFw���dQ�_���U�cZ���L�E�:�;�P���8��t3��s4�+���~)��w�QeP�l[����96�͊�B�?�~�fR��v5sE8�`�C���)��g�<��4}�zG��K��7��M�J��i>�C�28���O�d���֊El	_P����<a5�<^��b�4���	��6��+m�.S��mJw���M��vˣ7
�Q~"�P�BN�V�_Ӆ��A�b����-nx%ҽ����+.����]|n�v2H�T���H�����j��NI��pd�-�"�wj(
��*�?Ô#�(����L0:��{kMux��p�t�۸���w����"�L��z�ke�(r2�<�\�{s�Q�տfe��5��y]���j&JU�7�p`H���7q��/kZ�X-���Ur��p�bh��r���)��A�dÙǈ*�R�y!�r�~8����j�����*������Zr��1�"5�]w���<T��������Ƚ�N��>l���
�.4�%�\�"���G��m���Ne²�j&)��4�4�:悘#�⁑�c�c'��r�%��|��p��.\�:J�u���.��+�kT�?��2����^�b����n�E��E�|s����!�޺����Z�8Y�UB���������!�}����1���� ��Ϝ�/p�� l  ���1,���������r<ݨj���tl֙�"_wQP�j�c�,�lr�mr�$i!��.,{��`�Ɖ����4�M53�hK̆�t}}��>d�]�p�ɍ��P^��|�)�}"�f<roﾎ�ۂ�Ԩ���
�.U�o/	��6��?�Bu��%*���w`�1v�%w9���DF[a,ۓm	r����=�aqTh�أqH� շf������<�f�	�|��a��r�԰�Z �U|H�j�1�T�D����m�"�R����Mbq���s5�x�?Vo�0���j�t���I^oX�&��6��S�֨�h�e=tr̝o׋4�ӫΐO��ȡFe� m]b���¥J�;���s�A��Y���jW��f��u#��Z��[���h�GC8�|�4tnK���z�C8��@�<&P�p�b]��J�me�٫�lr�j&!�7�T�{�l��v�k�}%1�]����z"%jחk��B3H��A��>�u�+��� s�Ze���.�Xg�!H�6�������&��#VFO�p(n�����[�떞p��d���Ԍ�g�h�=��/p�_��4�~����� G%���@��!�1�׫֗yA���q�c���Nwڣ�(��1�qbhϱ�H�Xfq�	y��4��ny�Ν�U�W��{7��>T��!p�#":Џe�	˛k/Yi1S�W�-*ݹ��M��aeJEk7���>k�5`ߚ�m � ��׬���)�p
d5����ӻ�/���~��]�t�\�B�\��F9�k��;dG�=����j�Z�Z�Y�� |���1�N�@�T�{��:X���=s=F�i\���G���n+��zE]%�X����9'Z�_U���?�������9g      